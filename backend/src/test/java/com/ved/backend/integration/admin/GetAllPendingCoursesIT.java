package com.ved.backend.integration.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.configuration.CategoryProperties;
import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.RoleProperties;
import com.ved.backend.model.*;
import com.ved.backend.repo.*;
import com.ved.backend.service.AdminService;
import com.ved.backend.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
    locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class GetAllPendingCoursesIT {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AppUserRepo appUserRepo;

  @Autowired
  private AppRoleRepo appRoleRepo;

  @Autowired
  private RoleProperties roleProperties;

  @Autowired
  private CourseStateRepo courseStateRepo;

  @Autowired
  private CourseStateProperties courseStateProperties;

  @Autowired
  private CategoryRepo categoryRepo;

  @Autowired
  private CategoryProperties categoryProperties;

  @Autowired
  private CourseRepo courseRepo;

  @Autowired
  private AdminService adminService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private ObjectMapper objectMapper;

  private String instructorAccessToken;

  private String adminAccessToken;

  private Long createdCourseId;

  @BeforeEach
  void createInstructorCourse() throws Exception {
    // Create STUDENT role
    appRoleRepo.save(AppRole.builder()
        .name(roleProperties.getStudent())
        .build());

    // Create INSTRUCTOR role
    appRoleRepo.save(AppRole.builder()
        .name(roleProperties.getInstructor())
        .build());

    // Create INCOMPLETE course state
    courseStateRepo.save(CourseState.builder()
        .name(courseStateProperties.getIncomplete())
        .build());

    // Create PENDING course state
    courseStateRepo.save(CourseState.builder()
        .name(courseStateProperties.getPending())
        .build());

    // Create DESIGN course category
    categoryRepo.save(Category.builder()
        .name(categoryProperties.getDesign())
        .build());

    // Create new student
    String username = "test@test.com";
    String password = "password";
    Collection<AppRole> appRoles = new ArrayList<>();
    String firstName = "First";
    String lastName = "Last";
    Student student = Student.builder()
        .firstName(firstName)
        .lastName(lastName)
        .build();
    AppUser appUser = AppUser.builder()
        .username(username)
        .password(password)
        .appRoles(appRoles)
        .student(student)
        .build();
    String json = objectMapper.writeValueAsString(appUser);
    mockMvc.perform(post("/api/users/new-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isCreated());

    // Log in with new student
    Map<String, String> credentialContents = new HashMap<>();
    credentialContents.put("username", username);
    credentialContents.put("password", password);
    ResultActions logInActions = mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(credentialContents)))
        .andExpect(status().isOk());

    // Get access token
    String credentialJson = logInActions
        .andReturn()
        .getResponse()
        .getContentAsString();
    Map<String, Object> credentials = objectMapper.readValue(credentialJson, Map.class);
    instructorAccessToken = "Bearer " + credentials.get("access_token").toString();

    // Create new Instructor
    Instructor instructor = Instructor.builder()
        .recipientId("test_recipientId_112").build();
    mockMvc.perform(put("/api/students/instructor-feature")
            .header(AUTHORIZATION, instructorAccessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(instructor)))
        .andExpect(status().isOk());

    // Get refresh token
    Cookie refreshTokenCookie = logInActions
        .andReturn()
        .getResponse()
        .getCookie("refresh_token");

    // Get new access token
    logInActions = mockMvc.perform(get("/api/token/refresh")
        .cookie(refreshTokenCookie));
    credentialJson = logInActions
        .andReturn()
        .getResponse()
        .getContentAsString();
    credentials = objectMapper.readValue(credentialJson, Map.class);
    instructorAccessToken = "Bearer " + credentials.get("access_token").toString();

    // New course
    HashMap<String, String> assignment = new HashMap<>();
    String assignmentDetail = "assignment test";
    assignment.put("detail", assignmentDetail);

    HashMap<String, Object> section = new HashMap<>();
    String sectionName = "section name test";
    section.put("name", sectionName);

    Chapter chapter = Chapter.builder()
        .sections(List.of(section))
        .assignments(List.of(assignment))
        .build();

    Category category = categoryService.getByName(categoryProperties.getDesign());
    Category design = Category.builder()
        .id(category.getId())
        .name(category.getName())
        .build();

    String newCourseJson = objectMapper.writeValueAsString(
        Course.builder()
            .name("Course Name Test")
            .price(0L)
            .overview("Overview Test")
            .requirement("Requirement Test")
            .chapters(List.of(chapter))
            .category(design)
            .build());

    // Create new course
    ResultActions createCourseActions = mockMvc.perform(post("/api/instructors/course")
        .header(AUTHORIZATION, instructorAccessToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(newCourseJson));

    // Get created course id
    String URI = createCourseActions.andReturn().getResponse().getHeader("Location");
    assertThat(URI).isNotNull();
    String[] parts = URI.split("/incomplete-courses/");
    createdCourseId = Long.valueOf(parts[parts.length - 1]);

    // Submit Incomplete Course
    mockMvc.perform(put("/api/instructors/incomplete-courses/" + createdCourseId + "/state")
        .header(AUTHORIZATION, instructorAccessToken));
  }

  @BeforeEach
  void createAdminAndLogin() throws Exception {
    // Create ADMIN role
    appRoleRepo.save(AppRole.builder()
        .name(roleProperties.getAdmin())
        .build());

    // Create Admin user
    String username = "admin@test.com";
    String password = "password";
    Collection<AppRole> appRoles = new ArrayList<>();
    AppUser admin = AppUser.builder()
        .username(username)
        .password(password)
        .appRoles(appRoles)
        .build();
    adminService.registerAdmin(admin);

    // Login with new admin
    Map<String, String> credentialContents = new HashMap<>();
    credentialContents.put("username", username);
    credentialContents.put("password", password);
    ResultActions logInActions = mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(credentialContents)))
        .andExpect(status().isOk());

    // Get access token
    String credentialJson = logInActions
        .andReturn()
        .getResponse()
        .getContentAsString();
    Map<String, Object> credentials = objectMapper.readValue(credentialJson, Map.class);
    adminAccessToken = "Bearer " + credentials.get("access_token").toString();
  }

  @AfterEach
  void tearDown() {
    appUserRepo.deleteAll();
    appRoleRepo.deleteAll();
    courseStateRepo.deleteAll();
    categoryRepo.deleteAll();
    instructorAccessToken = null;
    adminAccessToken = null;
    createdCourseId = null;
  }

  @Test
  void given_whenGetAllPendingCourses_thenReturnPendingCourseInfo() throws Exception {
    // given
    long courseId = createdCourseId;

    // when
    ResultActions resultActions = mockMvc
        .perform(get("/api/admins/pending-courses")
            .header(AUTHORIZATION, adminAccessToken));

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(jsonPath("$").isArray());
  }
}

package com.ved.backend.integration.instructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.configuration.CategoryProperties;
import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.RoleProperties;
import com.ved.backend.model.*;
import com.ved.backend.repo.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
    locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class GetAllIncompleteCoursesIT {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CategoryService categoryService;

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
  private ObjectMapper objectMapper;

  private String accessToken;

  @BeforeEach
  void init() throws Exception {
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
    accessToken = "Bearer " + credentials.get("access_token").toString();

    // Create new Instructor
    Instructor instructor = Instructor.builder()
        .recipientId("test_recipientId_112").build();
    mockMvc.perform(put("/api/students/instructor-feature")
            .header(AUTHORIZATION, accessToken)
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
    accessToken = "Bearer " + credentials.get("access_token").toString();
  }

  @AfterEach
  void tearDown() {
    appUserRepo.deleteAll();
    appRoleRepo.deleteAll();
    courseStateRepo.deleteAll();
    categoryRepo.deleteAll();
    accessToken = null;
  }

  @Test
  void givenTwoIncompleteCourses_whenGetIncompleteCourses_ReturnIncompleteCourses() throws Exception {
    //given
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

    mockMvc.perform(post("/api/instructors/course")
        .header(AUTHORIZATION, accessToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(newCourseJson));
    mockMvc.perform(post("/api/instructors/course")
        .header(AUTHORIZATION, accessToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(newCourseJson));

    //when
    ResultActions resultActions = mockMvc
        .perform(get("/api/instructors/incomplete-courses")
            .header(AUTHORIZATION, accessToken));

    //then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(jsonPath("$.instructorFullName").value("First Last"))
        .andExpect(jsonPath("$.courses.length()").value(2));
  }

  @Test
  void givenNoIncompleteCourses_whenGetIncompleteCourse_ReturnEmptyArray() throws Exception {
    //given

    //when
    ResultActions resultActions = mockMvc
        .perform(get("/api/instructors/incomplete-courses")
            .header(AUTHORIZATION, accessToken));

    //then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(jsonPath("$.instructorFullName").value("First Last"))
        .andExpect(jsonPath("$.courses.length()").value(0));
  }
}

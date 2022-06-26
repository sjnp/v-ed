package com.ved.backend.integration.instructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.configuration.CategoryProperties;
import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.RoleProperties;
import com.ved.backend.model.*;
import com.ved.backend.repo.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
    locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class CreateCourseIT {

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
  private ObjectMapper objectMapper;

  private String accessToken;

  @BeforeEach
  void createInstructorAndLogin() throws Exception {
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
  void givenNewCourse_whenCreateCourse_thenReturnOk() throws Exception {
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

    String courseName = "Test Course's name";
    Long price = 0L;
    String overview = "Overview Test";
    String requirement = "Requirement Test";
    Long categoryId = categoryRepo.findByName(categoryProperties.getDesign()).get().getId();
    Category design = Category.builder()
        .id(categoryId)
        .name(categoryProperties.getDesign())
        .build();
    Course givenCourse = Course.builder()
        .name(courseName)
        .price(price)
        .overview(overview)
        .requirement(requirement)
        .chapters(List.of(chapter))
        .category(design)
        .build();

    //when
    String json = objectMapper.writeValueAsString(givenCourse);
    ResultActions resultActions = mockMvc.perform(post("/api/instructors/course")
        .header(AUTHORIZATION, accessToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    //then
    resultActions.andExpect(status().isCreated());
    MockHttpServletResponse response = resultActions.andReturn().getResponse();
    String URI = response.getHeader("Location");
    assertThat(URI).isNotNull();
    String[] parts = URI.split("/incomplete-courses/");
    Long courseId = Long.valueOf(parts[parts.length - 1]);
    Course expectedCourse = courseRepo
        .findById(courseId)
        .orElseThrow(() -> new RuntimeException("Can't find this course in database."));
    Category expectedCategory = expectedCourse.getCategory();
    CourseState expectedCourseState = expectedCourse.getCourseState();
    assertThat(expectedCourse.getName()).isEqualTo(givenCourse.getName());
    assertThat(expectedCourse.getRequirement()).isEqualTo(givenCourse.getRequirement());
    assertThat(expectedCourse.getOverview()).isEqualTo(givenCourse.getOverview());
    assertThat(expectedCourse.getPrice()).isEqualTo(givenCourse.getPrice());
    assertThat(expectedCategory.getName()).isEqualTo(givenCourse.getCategory().getName());
    assertThat(expectedCourseState.getName()).isEqualTo(courseStateProperties.getIncomplete());
  }
}

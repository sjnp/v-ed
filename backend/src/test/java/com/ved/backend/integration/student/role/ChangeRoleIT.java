package com.ved.backend.integration.student.role;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.configuration.RoleProperties;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.service.InstructorService;
import com.ved.backend.service.UserService;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
    locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class ChangeRoleIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private AppRoleRepo appRoleRepo;

  @Autowired
  private AppUserRepo appUserRepo;

  @Autowired
  private RoleProperties roleProperties;

  @Autowired
  private UserService userService;

  private ResultActions logInActions;

  @BeforeEach
  void createStudentAndLogin() throws Exception {
    // Build STUDENT role
    appRoleRepo.save(AppRole.builder()
        .name(roleProperties.getStudent())
        .build());

    // Build INSTRUCTOR role
    appRoleRepo.save(AppRole.builder()
        .name(roleProperties.getInstructor())
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
    logInActions = mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(credentialContents)))
        .andExpect(status().isOk());
  }

  @AfterEach
  void tearDown() {
    appUserRepo.deleteAll();
    appRoleRepo.deleteAll();
    logInActions = null;
  }

  @Test
  void givenLoggedInStudent_whenChangeRole_thenReturnOk() throws Exception {
    //given
    String username = "test@test.com";
    MockHttpServletResponse logInResponse = logInActions.andReturn().getResponse();
    String credentialJson = logInResponse.getContentAsString();
    Map<String, Object> credentials = objectMapper.readValue(credentialJson, Map.class);
    String accessToken = "Bearer " + credentials.get("access_token").toString();
    Instructor instructor = Instructor.builder()
        .recipientId("test_recipientId_112").build();

    //when
    ResultActions resultActions = mockMvc.perform(put("/api/students/instructor-feature")
        .header(AUTHORIZATION, accessToken)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(instructor)));

    //then
    resultActions.andExpect(status().isOk());
    assertThat(userService.getInstructor(username)).isNotNull();
  }
}

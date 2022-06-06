package com.ved.backend.integration.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.configuration.RoleProperties;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
    locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class LogInIT {
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

  @BeforeEach
  void setStudentRole() {
    appRoleRepo.save(AppRole.builder()
        .name(roleProperties.getStudent())
        .build());
  }

  @AfterEach
  void tearDown() {
    appUserRepo.deleteAll();
    appRoleRepo.deleteAll();
  }

  @Test
  void givenExistingUsername_whenLogIn_thenReturnToken() throws Exception {
    //given
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

    Map<String, String> credentialContents = new HashMap<>();
    credentialContents.put("username", username.toUpperCase());
    credentialContents.put("password", password);

    //when
    ResultActions resultActions = mockMvc.perform(post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(credentialContents)));

    //then
    resultActions.andExpect(status().isOk());
    MockHttpServletResponse response = resultActions.andReturn().getResponse();
    Cookie refreshTokenCookie = response.getCookie("refresh_token");
    String content = response.getContentAsString();
    assertThat(refreshTokenCookie).isNotNull();
    assertThat(refreshTokenCookie.isHttpOnly()).isTrue();
    assertThat(content).contains("access_token");
    assertThat(content).contains(roleProperties.getStudent());
  }

  @Test
  void givenWrongPassword_whenLogIn_thenReturnUnauthorized() throws Exception {
    //given
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

    String wrongPassword = "p4sSw0rD";
    Map<String, String> credentialContents = new HashMap<>();
    credentialContents.put("username", username);
    credentialContents.put("password", wrongPassword);

    //when
    ResultActions resultActions = mockMvc.perform(post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(credentialContents)));

    //then
    resultActions.andExpect(status().isUnauthorized());
  }

  @Test
  void givenUnknownUsername_whenLogIn_thenReturnUnauthorized() throws Exception {
    //given
    String username = "test@test.com";
    String password = "password";

    Map<String, String> credentialContents = new HashMap<>();
    credentialContents.put("username", username);
    credentialContents.put("password", password);

    //when
    ResultActions resultActions = mockMvc.perform(post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(credentialContents)));

    //then
    resultActions.andExpect(status().isUnauthorized());
  }

  @Test
  void givenOnlyUsername_whenLogIn_thenReturnUnauthorized() throws Exception {
    //given
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

    Map<String, String> credentialContents = new HashMap<>();
    credentialContents.put("username", username);

    //when
    ResultActions resultActions = mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(credentialContents)));

    //then
    resultActions.andExpect(status().isUnauthorized());
  }

  @Test
  void givenOnlyPassword_whenLogIn_thenReturnUnauthorized() throws Exception {
    //given
    String password = "password";

    Map<String, String> credentialContents = new HashMap<>();
    credentialContents.put("password", password);

    //when
    ResultActions resultActions = mockMvc.perform(post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(credentialContents)));

    //then
    resultActions.andExpect(status().isUnauthorized());
  }
}
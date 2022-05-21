package com.ved.backend.integration;

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
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
    locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class ManageTokenIT {
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

  private ResultActions logInActions;

  @BeforeEach
  void createStudentAndLogin() throws Exception {
    appRoleRepo.save(AppRole.builder()
        .name(roleProperties.getStudent())
        .build());
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
  void givenValidRefreshToken_whenRefreshNewAccessToken_thenReturnNewAccessToken() throws Exception {
    //given
    String username = "test@test.com";
    MockHttpServletResponse logInResponse = logInActions.andReturn().getResponse();
    Cookie refreshTokenCookie = logInResponse.getCookie("refresh_token");
    assertThat(refreshTokenCookie).isNotNull();

    //when
    ResultActions resultActions = mockMvc.perform(get("/api/token/refresh")
        .cookie(refreshTokenCookie));

    //then
    resultActions.andExpect(status().isOk());
    MockHttpServletResponse response = resultActions.andReturn().getResponse();
    String content = response.getContentAsString();
    assertThat(content).contains("access_token");
    assertThat(content).contains(roleProperties.getStudent());
    assertThat(content).contains(username);
  }

  @Test
  void givenNoRefreshToken_whenRefreshNewAccessToken_thenReturnBadRequest() throws Exception {
    //given
    MockHttpServletResponse logInResponse = logInActions.andReturn().getResponse();
    Cookie refreshTokenCookie = logInResponse.getCookie("refresh_token");
    assertThat(refreshTokenCookie).isNotNull();
    refreshTokenCookie.setValue("");


    //when
    ResultActions resultActions = mockMvc.perform(get("/api/token/refresh")
            .cookie(refreshTokenCookie));

    //then
    resultActions.andExpect(status().isBadRequest())
        .andExpect(status().reason(containsString("Refresh token is missing")));
  }

  @Test
  void givenInvalidRefreshToken_whenRefreshNewAccessToken_thenReturnForbidden() throws Exception {
    //given
    MockHttpServletResponse logInResponse = logInActions.andReturn().getResponse();
    Cookie refreshTokenCookie = logInResponse.getCookie("refresh_token");
    assertThat(refreshTokenCookie).isNotNull();
    refreshTokenCookie.setValue("ThisIsGibberishRefreshToken");


    //when
    ResultActions resultActions = mockMvc.perform(get("/api/token/refresh")
        .cookie(refreshTokenCookie));

    //then
    resultActions.andExpect(status().isForbidden());
  }
}
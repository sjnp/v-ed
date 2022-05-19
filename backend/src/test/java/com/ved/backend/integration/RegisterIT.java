package com.ved.backend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
    locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class RegisterIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AppUserRepo appUserRepo;

  @Autowired
  private ObjectMapper objectMapper;

  @AfterEach
  void tearDown() {
    appUserRepo.deleteAll();
  }

  @Test
  void givenNewUsername_whenCreateNewStudent_thenReturnCreated() throws Exception {
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

    //when
    String json = objectMapper.writeValueAsString(appUser);
    ResultActions resultActions = mockMvc
        .perform(post("/api/users/new-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json));

    //then
    resultActions.andExpect(status().isCreated());
    Optional<AppUser> expected = appUserRepo.findAppUserByUsername(username);
    assertThat(expected.isPresent()).isEqualTo(true);
  }

  @Test
  void givenExistingUsername_whenCreateNewStudent_thenReturnConflicted() throws Exception {
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

    //when
    ResultActions resultActions = mockMvc
        .perform(post("/api/users/new-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json));

    //then
    resultActions.andExpect(status().isConflict())
        .andExpect(status().reason(containsString("already exists")));
  }
}

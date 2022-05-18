package com.ved.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

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
public class UserControllerIT {

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
  @Transactional
  void givenNewUsername_whenCreateNewStudent_thenReturnCreated() throws Exception {
    //given
    String username = "test@test.com";
    String password = "password";
    Collection<AppRole> appRoles = new ArrayList<>();
    String firstName = "First";
    String lastName = "Last";
    Student student = new Student(
        null,
        firstName,
        lastName,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    );
    AppUser appUser = new AppUser(
        null,
        username,
        password,
        appRoles,
        student
    );

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
    assertThat(expected.get().getUsername())
        .isEqualTo(appUser.getUsername());
    assertThat(expected.get().getStudent().getFirstName())
        .isEqualTo(appUser.getStudent().getFirstName());
    assertThat(expected.get().getStudent().getLastName())
        .isEqualTo(appUser.getStudent().getLastName());
  }

  @Test
  void givenExistingUsername_whenCreateNewStudent_thenReturnConflicted() throws Exception {
    //given
    String username = "test@test.com";
    String password = "password";
    Collection<AppRole> appRoles = new ArrayList<>();
    String firstName = "First";
    String lastName = "Last";
    Student student = new Student(
        null,
        firstName,
        lastName,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    );
    AppUser appUser = new AppUser(
        null,
        username,
        password,
        appRoles,
        student
    );
    appUserRepo.save(appUser);

    //when
    String json = objectMapper.writeValueAsString(appUser);
    ResultActions resultActions = mockMvc
        .perform(post("/api/users/new-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json));

    //then
    resultActions.andExpect(status().isConflict())
        .andExpect(status().reason(containsString("already exists")));
  }
}

package com.ved.backend.service;

import com.ved.backend.exception.ConflictException;
import com.ved.backend.exception.NotFoundException;
import com.ved.backend.exception.UnauthorizedException;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.InstructorRepo;
import com.ved.backend.repo.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private AppUserRepo appUserRepo;

  @Mock
  private AppRoleRepo appRoleRepo;

  @Mock
  private StudentRepo studentRepo;

  @Mock
  private InstructorRepo instructorRepo;

  @Mock
  private PasswordEncoder passwordEncoder;

  private UserService underTest;

  @BeforeEach
  void setUp() {
    underTest = new UserService(
        appUserRepo,
        appRoleRepo,
        studentRepo,
        instructorRepo,
        passwordEncoder
    );
  }

  @Test
  void givenExistingUsername_whenLoadUser_thenReturnUserDetails() {
    //given
    String username = "test@test.com";
    String password = "password";
    Collection<AppRole> appRoles = new ArrayList<>();
    AppUser appUser = AppUser.builder()
        .username(username)
        .password("password")
//        .appRoles(new ArrayList<>())
        .build();

    given(appUserRepo.findAppUserByUsername(username)).willReturn(Optional.of(appUser));

    //when
    UserDetails expected = underTest.loadUserByUsername(username);

    //then
    assertThat(expected.getUsername()).isEqualTo(username);
  }

  @Test
  void givenUnknownUsername_whenLoadUser_thenThrow() {

    //given
    String username = "test@test.com";

    //when
    //then
    assertThatThrownBy(() -> underTest.loadUserByUsername(username))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessageContaining("User with username: " + username + " does not exist");
  }

  @Test
  void givenNewAppUser_whenRegister_thenSuccess() {
    //given
    Collection<AppRole> appRoles = new ArrayList<>();
    AppUser appUser = new AppUser(
        null,
        "test@test.com",
        "password",
        appRoles,
        null
    );

    //when
    underTest.registerStudent(appUser);

    //then
    ArgumentCaptor<AppUser> appUserArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);

    verify(appUserRepo).save(appUserArgumentCaptor.capture());

    AppUser capturedAppUser = appUserArgumentCaptor.getValue();

    assertThat(capturedAppUser).isEqualTo(appUser);
  }

  @Test
  void givenExistingUsername_whenRegister_thenThrow() {
    //given
    Collection<AppRole> appRoles = new ArrayList<>();
    AppUser appUser = new AppUser(
        null,
        "test@test.com",
        "password",
        appRoles,
        null
    );
    given(appUserRepo.existsByUsername(anyString())).willReturn(true);

    //when
    //then
    assertThatThrownBy(() -> underTest.registerStudent(appUser))
        .isInstanceOf(ConflictException.class)
        .hasMessageContaining("User with username: " + appUser.getUsername() + " already exists");
  }

  @Test
  void givenExistingUsername_whenGetUser_thenReturnThatUser() {
    //given
    String username = "test@test.com";
    Optional<AppUser> appUser = Optional.of(new AppUser(
        null,
        username,
        "password",
        null,
        null
    ));
    given(appUserRepo.findAppUserByUsername(username)).willReturn(appUser);

    //when
    AppUser expected = underTest.getAppUser(username);

    //then
    verify(appUserRepo).findAppUserByUsername(username);
    assertThat(expected.getUsername()).isEqualTo(username);
  }

  @Test
  void givenUnknownUsername_whenGetUser_thenThrow() {
    //given
    String username = "test@test.com";

    //when
    //then
    assertThatThrownBy(() -> underTest.getAppUser(username))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("User with username: " + username + " does not exist");
  }

  @Test
  void givenStudentUsername_whenGetStudent_thenReturnThatStudent() {
    //given
    UserService underThisTest = spy(underTest);
    String username = "test@test.com";
    AppUser appUser = new AppUser();
    Student student = new Student();

    doReturn(appUser)
        .when(underThisTest)
        .getAppUser(anyString());

    given(studentRepo.findByAppUser(any(AppUser.class)))
        .willReturn(Optional.of(student));

    //when
    Student expected = underThisTest.getStudent(username);

    //then
    verify(studentRepo).findByAppUser(any(AppUser.class));
    assertThat(expected).isEqualTo(student);
  }

  @Test
  void givenNotStudentUsername_whenGetStudent_thenThrow() {
    //given
    UserService underThisTest = spy(underTest);
    String username = "test@test.com";
    AppUser appUser = new AppUser();

    doReturn(appUser)
        .when(underThisTest)
        .getAppUser(anyString());

    given(studentRepo.findByAppUser(any(AppUser.class)))
        .willReturn(Optional.empty());

    //when
    //then
    assertThatThrownBy(() -> underThisTest.getStudent(username))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessageContaining("User with username: " + username + " is not a student");
  }

  @Test
  void givenInstructorUsername_whenGetInstructor_thenReturnThatInstructor() {
    //given
    UserService underThisTest = spy(underTest);
    Student student = new Student();
    Instructor instructor = new Instructor();

    doReturn(student)
        .when(underThisTest)
        .getStudent(anyString());

    given(instructorRepo.findByStudent(any(Student.class)))
        .willReturn(Optional.of(instructor));

    //when
    Instructor expected = underThisTest.getInstructor(anyString());

    //then
    verify(instructorRepo).findByStudent(any(Student.class));
    assertThat(expected).isEqualTo(instructor);
  }

  @Test
  void givenNotInstructorUsername_whenGetInstructor_thenThrow() {
    //given
    UserService underThisTest = spy(underTest);
    String username = "test@test.com";
    Student student = new Student();

    doReturn(student)
        .when(underThisTest)
        .getStudent(anyString());

    given(instructorRepo.findByStudent(any(Student.class)))
        .willReturn(Optional.empty());

    //when
    //then
    assertThatThrownBy(() -> underThisTest.getInstructor(username))
        .isInstanceOf(UnauthorizedException.class)
        .hasMessageContaining("User with username: " + username + " is not an instructor");
  }
}
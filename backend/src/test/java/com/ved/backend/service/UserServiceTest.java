package com.ved.backend.service;

import com.ved.backend.exception.ConflictException;
import com.ved.backend.exception.NotFoundException;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private AppUserRepo appUserRepo;

  @Mock
  private AppRoleRepo appRoleRepo;

  @Mock
  private PasswordEncoder passwordEncoder;

  private UserService underTest;

  @BeforeEach
  void setUp() {
    underTest = new UserService(appUserRepo, appRoleRepo, passwordEncoder);
  }

  @Test
  void canLoadUserByUsername() {
    //given
    String username = "test@test.com";
    Collection<AppRole> appRoles = new ArrayList<>();
    Optional<AppUser> appUser = Optional.of(new AppUser(
        null,
        username,
        "password",
        appRoles,
        null
    ));
    given(appUserRepo.findAppUserByUsername(username)).willReturn(appUser);

    //when
    UserDetails expected = underTest.loadUserByUsername(username);

    //then
    assertThat(expected.getUsername()).isEqualTo(username);
  }

  @Test
  void willThrowWhenCanNotLoadUserByUsername() {
    //given
    String username = "test@test.com";

    //when
    //then
    assertThatThrownBy(() -> underTest.loadUserByUsername(username))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessageContaining("User with username: " + username + " does not exist");
  }

  @Test
  void canRegisterStudent() {
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
  void willThrowWhenCanNotRegister() {
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
  void canGetAppUser() {
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
    assertThat(expected.getUsername()).isEqualTo(appUser.get().getUsername());
  }

  @Test
  void willThrowWhenCanNotGetAppUser() {
    //given
    String username = "test@test.com";

    //when
    //then
    assertThatThrownBy(() -> underTest.getAppUser(username))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("User with username: " + username + " does not exist");
  }
}
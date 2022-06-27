package com.ved.backend.service.adminService;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.RoleProperties;
import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.service.AdminService;
import com.ved.backend.service.CourseService;
import com.ved.backend.service.CourseStateService;
import com.ved.backend.service.PrivateObjectStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

  @Mock
  private AppUserRepo appUserRepo;

  @Mock
  private AppRoleRepo appRoleRepo;

  @Mock
  private CourseRepo courseRepo;

  @Mock
  private CourseStateProperties courseStateProperties;

  @Mock
  private RoleProperties roleProperties;

  @Mock
  private CourseStateService courseStateService;

  @Mock
  private CourseService courseService;

  @Mock
  private PrivateObjectStorageService privateObjectStorageService;

  @Mock
  private PasswordEncoder passwordEncoder;

  private AdminService underTest;

  @BeforeEach
  void setup() {
    underTest = new AdminService(
        appUserRepo,
        appRoleRepo,
        courseRepo,
        courseStateProperties,
        roleProperties,
        courseStateService,
        courseService,
        privateObjectStorageService,
        passwordEncoder);
  }

  @Test
  void givenExistingUsername_whenRegister_thenThrow() {
    //given
    AppUser appUser = AppUser.builder()
        .username("test")
        .password("password")
        .build();
    given(appUserRepo.existsByUsername(anyString())).willReturn(true);

    //when
    //then
    assertThatThrownBy(() -> underTest.registerAdmin(appUser))
        .isInstanceOf(ConflictException.class)
        .hasMessageContaining("User with username: " + appUser.getUsername() + " already exists");
  }

  @Test
  void givenNewAppUser_whenRegister_thenSuccess() {
    //given
    AppUser appUser = AppUser.builder()
        .username("test")
        .password("password")
        .appRoles(new ArrayList<>())
        .build();
    AppRole adminRole = AppRole.builder()
        .id(1L)
        .name("ADMIN")
        .build();
    given(appUserRepo.existsByUsername(anyString())).willReturn(false);
    given(roleProperties.getAdmin()).willReturn("ADMIN");
    given(appRoleRepo.findByName(anyString())).willReturn(adminRole);

    //when
    underTest.registerAdmin(appUser);

    //then
    ArgumentCaptor<AppUser> appUserArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
    verify(appUserRepo).save(appUserArgumentCaptor.capture());
  }
}

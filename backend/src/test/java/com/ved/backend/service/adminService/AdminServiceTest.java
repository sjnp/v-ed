package com.ved.backend.service.adminService;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.RoleProperties;
import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.exception.baseException.NotFoundException;
import com.ved.backend.model.*;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.response.FullPendingCourseInfoDto;
import com.ved.backend.response.PendingCourseResponse;
import com.ved.backend.response.VideoResponse;
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
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

  @Test
  void givenUsername_whenGetPendingCourses_thenReturnPendingCourses() {
    //given
    String username = "admin";
    Student student = Student.builder()
        .firstName("First")
        .lastName("Last")
        .build();
    Instructor instructor = Instructor.builder()
        .student(student)
        .build();
    Course pendingCourse = Course.builder()
        .id(1L)
        .name("Pending Course")
        .instructor(instructor)
        .build();
    CourseState pendingState = CourseState.builder()
        .name("PENDING")
        .build();
    given(courseStateProperties.getPending()).willReturn("PENDING");
    given(courseStateService.getByName(anyString())).willReturn(pendingState);
    given(courseRepo.findCoursesByCourseState(pendingState)).willReturn(List.of(pendingCourse));

    //when
    List<PendingCourseResponse> pendingCourses = underTest.getPendingCourses(username);

    //then
    assertThat(pendingCourses.size()).isEqualTo(1);
    assertThat(pendingCourses.get(0).getId()).isEqualTo(pendingCourse.getId());
    assertThat(pendingCourses.get(0).getName()).isEqualTo(pendingCourse.getName());
    assertThat(pendingCourses.get(0).getInstructorName())
        .isEqualTo(pendingCourse.getInstructor().getStudent().getFullName());
  }

  @Test
  void givenCourseIdAndUsername_whenGetPendingCourse_thenReturnPendingCourse() {
    //given
    String username = "admin";
    Long courseId = 1L;

    Student student = Student.builder()
        .firstName("First")
        .lastName("Last")
        .biography("Bio")
        .occupation("Job")
        .profilePicUri("pic.com")
        .build();
    Instructor instructor = Instructor.builder()
        .student(student)
        .build();
    Category art = Category.builder()
        .name("ART")
        .build();
    Course pendingCourse = Course.builder()
        .id(courseId)
        .name("Pending Course")
        .instructor(instructor)
        .price(444L)
        .pictureUrl("course-pic.com")
        .category(art)
        .requirement("Require")
        .overview("Overview")
        .chapters(List.of(new Chapter()))
        .build();
    CourseState pendingState = CourseState.builder()
        .name("PENDING")
        .build();
    given(courseStateProperties.getPending()).willReturn("PENDING");
    given(courseStateService.getByName(anyString())).willReturn(pendingState);
    given(courseService.getByIdAndCourseState(courseId, pendingState)).willReturn(pendingCourse);

    //when
    FullPendingCourseInfoDto dto = underTest.getPendingCourse(courseId, username);

    //then
    assertThat(dto.getName()).isEqualTo(pendingCourse.getName());
    assertThat(dto.getPrice()).isEqualTo(pendingCourse.getPrice());
    assertThat(dto.getPictureUrl()).isEqualTo(pendingCourse.getPictureUrl());
    assertThat(dto.getInstructorInfo().getFirstName())
        .isEqualTo(pendingCourse.getInstructor().getStudent().getFirstName());
    assertThat(dto.getInstructorInfo().getLastName())
        .isEqualTo(pendingCourse.getInstructor().getStudent().getLastName());
    assertThat(dto.getInstructorInfo().getBiography())
        .isEqualTo(pendingCourse.getInstructor().getStudent().getBiography());
    assertThat(dto.getInstructorInfo().getOccupation())
        .isEqualTo(pendingCourse.getInstructor().getStudent().getOccupation());
    assertThat(dto.getInstructorInfo().getProfilePicUri())
        .isEqualTo(pendingCourse.getInstructor().getStudent().getProfilePicUri());
    assertThat(dto.getCategory()).isEqualTo(pendingCourse.getCategory().getName());
    assertThat(dto.getRequirement()).isEqualTo(pendingCourse.getRequirement());
    assertThat(dto.getOverview()).isEqualTo(pendingCourse.getOverview());
    assertThat(dto.getChapters()).isEqualTo(pendingCourse.getChapters());
  }

  @Test
  void givenCourseIdAndInvalidChapterIndex_whenGetVideoUrl_thenThrow() {
    //given
    Long courseId = 1L;
    Integer invalidChapterIndex = 2;
    Integer sectionIndex = 0;
    String username = "admin";

    given(courseStateProperties.getPending()).willReturn("PENDING");
    CourseState pendingState = CourseState.builder()
        .name("PENDING")
        .build();
    given(courseStateService.getByName(anyString())).willReturn(pendingState);
    HashMap<String, Object> section = new HashMap<>();
    section.put("videoUri", "video.com");
    Chapter chapter = Chapter.builder()
        .sections(List.of(section))
        .build();
    Course course = Course.builder()
        .chapters(List.of(chapter))
        .build();
    given(courseService.getByIdAndCourseState(courseId, pendingState)).willReturn(course);

    //when
    //then
    assertThatThrownBy(() -> underTest
        .getVideoUrlFromPendingCourse(courseId, invalidChapterIndex, sectionIndex, username))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Video not found");
  }

  @Test
  void givenCourseIdAndInvalidSectionIndex_whenGetVideoUrl_thenThrow() {
    //given
    Long courseId = 1L;
    Integer invalidChapterIndex = 0;
    Integer sectionIndex = 1;
    String username = "admin";

    given(courseStateProperties.getPending()).willReturn("PENDING");
    CourseState pendingState = CourseState.builder()
        .name("PENDING")
        .build();
    given(courseStateService.getByName(anyString())).willReturn(pendingState);
    HashMap<String, Object> section = new HashMap<>();
    section.put("videoUri", "video.com");
    Chapter chapter = Chapter.builder()
        .sections(List.of(section))
        .build();
    Course course = Course.builder()
        .chapters(List.of(chapter))
        .build();
    given(courseService.getByIdAndCourseState(courseId, pendingState)).willReturn(course);

    //when
    //then
    assertThatThrownBy(() -> underTest
        .getVideoUrlFromPendingCourse(courseId, invalidChapterIndex, sectionIndex, username))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Video not found");
  }

  @Test
  void givenCourseIdAndChapterIndexAndSectionIndexAndUsername_whenGetVideoUrl_thenThrow() {
    //given
    Long courseId = 1L;
    Integer invalidChapterIndex = 0;
    Integer sectionIndex = 0;
    String username = "admin";
    String videoFileName = "video.mp4";

    given(courseStateProperties.getPending()).willReturn("PENDING");
    CourseState pendingState = CourseState.builder()
        .name("PENDING")
        .build();
    given(courseStateService.getByName(anyString())).willReturn(pendingState);
    HashMap<String, Object> section = new HashMap<>();
    section.put("videoUri", videoFileName);
    Chapter chapter = Chapter.builder()
        .sections(List.of(section))
        .build();
    Course course = Course.builder()
        .chapters(List.of(chapter))
        .build();
    given(courseService.getByIdAndCourseState(courseId, pendingState)).willReturn(course);
    String videoUrl = "full-url.com/video.mp4";
    given(privateObjectStorageService.readFile(videoFileName, username)).willReturn(videoUrl);

    //when
    VideoResponse videoResponse = underTest
        .getVideoUrlFromPendingCourse(courseId, invalidChapterIndex, sectionIndex, username);

    //then
    assertThat(videoResponse.getVideoUrl()).isEqualTo(videoUrl);
  }
}

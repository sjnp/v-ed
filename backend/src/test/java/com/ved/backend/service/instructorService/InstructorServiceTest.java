package com.ved.backend.service.instructorService;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.PublicObjectStorageConfigProperties;
import com.ved.backend.model.*;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.repo.InstructorRepo;
import com.ved.backend.service.InstructorService;
import com.ved.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {

  @Mock
  private AppUserRepo appUserRepo;

  @Mock
  private CourseRepo courseRepo;

  @Mock
  private CourseStateRepo courseStateRepo;

  @Mock
  private InstructorRepo instructorRepo;

  @Mock
  private UserService userService;

  @Mock
  private CourseStateProperties courseStateProperties;

  @Mock
  private PublicObjectStorageConfigProperties publicObjectStorageConfigProperties;

  private InstructorService underTest;

  @BeforeEach
  void setup() {
//    underTest = new InstructorService(
//        appUserRepo,
//        courseRepo,
//        courseStateRepo,
//        instructorRepo,
//        userService,
//        courseStateProperties,
//        publicObjectStorageConfigProperties
//    );
  }

  @Test
  void givenNewCourse_whenCreateCourse_thenReturnPayload() {

    //given
    String username = "test@test.com";
    Instructor instructor = Instructor.builder()
        .courses(new ArrayList<>())
        .build();
    CourseState incompleteState = CourseState.builder()
        .name(courseStateProperties.getIncomplete())
        .build();
    Course newCourse = Course.builder().build();
    Long courseId = 1L;

    given(userService.getInstructor(username)).willReturn(instructor);
    given(courseStateRepo.findByName(courseStateProperties.getIncomplete()))
        .willReturn(incompleteState);
    given(courseRepo.save(newCourse))
        .will(i -> {
          newCourse.setId(courseId);
          return null;
        });

    //when
    HashMap<String, Long> expected = underTest.createCourse(newCourse, username);

    //then
    ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
    verify(courseRepo).save(courseArgumentCaptor.capture());
    Course capturedCourse = courseArgumentCaptor.getValue();
    assertThat(capturedCourse).isEqualTo(newCourse);

    ArgumentCaptor<Instructor> instructorArgumentCaptor = ArgumentCaptor.forClass(Instructor.class);
    verify(instructorRepo).save(instructorArgumentCaptor.capture());
    Instructor capturedInstructor = instructorArgumentCaptor.getValue();
    assertThat(capturedInstructor).isEqualTo(instructor);

    assertThat(expected.get("id")).isEqualTo(newCourse.getId());
  }

}
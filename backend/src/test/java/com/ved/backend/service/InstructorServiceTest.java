package com.ved.backend.service;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.PublicObjectStorageConfigProperties;
import com.ved.backend.model.*;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.repo.InstructorRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {

  @Mock
  private AppUserRepo appUserRepo;

  @Mock
  private  CourseRepo courseRepo;

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
//    InstructorService underThisTest = spy(underTest);
//
//    String username = "test@test.com";
//    AppUser appUser = new AppUser();
//    Student student = new Student();
//    Instructor instructor = new Instructor();
//    instructor.setCourses(new ArrayList<>());
//    Course course = new Course();
//    CourseState courseState = new CourseState();
//
//    doReturn(instructor).when(underThisTest).getInstructor(username);
//    given(courseStateRepo.findByName(courseStateProperties.getIncomplete())).willReturn(courseState);
//
//    //when
//    HashMap<String, Long> expected = underThisTest.createCourse(course, username);
//
//    //then
//    ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
//    verify(courseRepo).save(courseArgumentCaptor.capture());
//    Course capturedCourse = courseArgumentCaptor.getValue();
//    assertThat(capturedCourse).isEqualTo(course);
//
//    ArgumentCaptor<Instructor> instructorArgumentCaptor = ArgumentCaptor.forClass(Instructor.class);
//    verify(instructorRepo).save(instructorArgumentCaptor.capture());
//    Instructor capturedInstructor = instructorArgumentCaptor.getValue();
//    assertThat(capturedInstructor).isEqualTo(instructor);
  }

}
package com.ved.backend.service.instructorService;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.PrivateObjectStorageConfigProperties;
import com.ved.backend.configuration.PublicObjectStorageConfigProperties;
import com.ved.backend.exception.CourseNotFoundException;
import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.exception.baseException.NotFoundException;
import com.ved.backend.model.*;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.repo.InstructorRepo;
import com.ved.backend.response.CourseResponse;
import com.ved.backend.response.IncompleteCourseResponse;
import com.ved.backend.response.PublishedCourseInfoResponse;
import com.ved.backend.service.*;
import com.ved.backend.utility.FileExtensionStringHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {

  @Mock
  private CourseRepo courseRepo;

  @Mock
  private InstructorRepo instructorRepo;

  @Mock
  private UserService userService;

  @Mock
  private OmiseService omiseService;

  @Mock
  private CourseStateService courseStateService;

  @Mock
  private PublicObjectStorageService publicObjectStorageService;

  @Mock
  private PrivateObjectStorageService privateObjectStorageService;

  @Mock
  private FileExtensionStringHandler fileExtensionStringHandler;

  @Mock
  private CourseStateProperties courseStateProperties;

  @Mock
  private PublicObjectStorageConfigProperties publicObjectStorageConfigProperties;

  @Mock
  private PrivateObjectStorageConfigProperties privateObjectStorageConfigProperties;

  private InstructorService underTest;

  @BeforeEach
  void setup() {
     underTest = new InstructorService(
         courseRepo,
         instructorRepo,
         userService,
         omiseService,
         courseStateService,
         publicObjectStorageService,
         privateObjectStorageService,
         fileExtensionStringHandler,
         courseStateProperties,
         publicObjectStorageConfigProperties,
         privateObjectStorageConfigProperties
     );
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
    given(courseStateService.getByName(courseStateProperties.getIncomplete()))
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

  @Test
  void givenIdAndUsernameAndState_whenGetCourse_thenReturnCourse() {
    //given
    Long courseId = 1L;
    String username = "test@test.com";
    Instructor instructor = Instructor.builder()
        .courses(new ArrayList<>())
        .build();
    CourseState incompleteState = CourseState.builder()
        .name(courseStateProperties.getIncomplete())
        .build();
    Course course = Course.builder()
        .id(courseId)
        .instructor(instructor)
        .courseState(incompleteState)
        .build();

    given(userService.getInstructor(username)).willReturn(instructor);
    given(courseStateService.getByName(courseStateProperties.getIncomplete()))
        .willReturn(incompleteState);
    given(courseRepo.findByInstructorAndCourseStateAndId(instructor, incompleteState, courseId))
        .willReturn(Optional.ofNullable(course));

    //when
    Course expected = underTest.getCourse(courseId, username, courseStateProperties.getIncomplete());

    //then
    assertThat(expected).isEqualTo(course);
  }

  @Test
  void givenUnknownIdAndUsernameAndState_whenGetCourse_thenThrow() {
    //given
    Long courseId = 1L;
    String username = "test@test.com";
    Instructor instructor = Instructor.builder()
        .courses(new ArrayList<>())
        .build();
    CourseState incompleteState = CourseState.builder()
        .name(courseStateProperties.getIncomplete())
        .build();

    given(userService.getInstructor(username)).willReturn(instructor);
    given(courseStateService.getByName(courseStateProperties.getIncomplete()))
        .willReturn(incompleteState);
    given(courseRepo.findByInstructorAndCourseStateAndId(instructor, incompleteState, courseId))
        .willReturn(Optional.empty());

    //when
    //then
    assertThatThrownBy(() -> underTest.getCourse(courseId, username, courseStateProperties.getIncomplete()))
        .isInstanceOf(CourseNotFoundException.class);
  }

  @Test
  void givenIdAndUsername_whenGetIncompleteCourseDetails_thenReturn() {
    //given
    InstructorService underThisTest = spy(underTest);
    Long courseId = 1L;
    String username = "test@test.com";
    Course incompleteCourse = Course.builder()
        .id(courseId)
        .name("course_name")
        .price(9999L)
        .pictureUrl("test.com/test.jpg")
        .chapters(List.of(new Chapter()))
        .build();

    given(courseStateProperties.getIncomplete()).willReturn("");
    doReturn(incompleteCourse)
        .when(underThisTest)
        .getCourse(anyLong(), anyString(), anyString());

    //when
    IncompleteCourseResponse expected = underThisTest.getIncompleteCourseDetails(courseId, username);

    //then
    assertThat(expected.getId()).isEqualTo(incompleteCourse.getId());
    assertThat(expected.getName()).isEqualTo(incompleteCourse.getName());
    assertThat(expected.getPrice()).isEqualTo(incompleteCourse.getPrice());
    assertThat(expected.getPictureUrl()).isEqualTo(incompleteCourse.getPictureUrl());
    assertThat(expected.getChapters()).isEqualTo(incompleteCourse.getChapters());
  }

  @Test
  void givenIdAndFileNameAndUsername_whenCreateParToUploadCoursePicture_thenReturnMapOfUrl() {
    //given
    InstructorService underThisTest = spy(underTest);
    Long courseId = 1L;
    String fileName = "file.jpg";
    String username = "test@test.com";
    String pictureExtension = "jpg";
    IncompleteCourseResponse incompleteCourseResponse = IncompleteCourseResponse
        .builder()
        .id(courseId)
        .pictureUrl(fileName)
        .build();
    String objectName = "course_pic_" + incompleteCourseResponse.getId() + "." + pictureExtension;
    String parUrl = "par.test/file.jpg";

    given(publicObjectStorageConfigProperties.getViableImageExtensions()).willReturn(List.of(pictureExtension));
    given(fileExtensionStringHandler.getViableExtension(fileName, publicObjectStorageConfigProperties.getViableImageExtensions()))
        .willReturn(pictureExtension);
    doReturn(incompleteCourseResponse)
        .when(underThisTest)
        .getIncompleteCourseDetails(anyLong(), anyString());
    given(publicObjectStorageService.uploadFile(objectName, username)).willReturn(parUrl);

    //when
    Map<String, String> expected = underThisTest.createParToUploadCoursePicture(courseId, fileName, username);

    //then
    assertThat(expected.get("preauthenticatedRequestUrl")).isEqualTo(parUrl);
  }

  @Test
  void givenIdAndInvalidObjectNameAndUsername_whenSaveCoursePictureUrl_thenThrow() {
    //given
    Long courseId = 1L;
    String username = "test@test.com";
    String objectName = "invalid.jpg";

    //when
    //then
    assertThatThrownBy(() -> underTest.saveCoursePictureUrl(courseId, objectName, username))
        .isInstanceOf(BadRequestException.class);
  }
  @Test
  void givenIdAndObjectNameAndUsername_whenSaveCoursePictureUrl_thenReturnPicUrl() {
    //given
    InstructorService underThisTest = spy(underTest);
    Long courseId = 1L;
    String username = "test@test.com";
    Course incompleteCourse = Course.builder()
        .id(courseId)
        .name("course_name")
        .price(9999L)
        .chapters(List.of(new Chapter()))
        .build();
    String objectName = "course_pic_" + courseId + ".jpg";
    String osUri = "regional.objectstorage.com";
    String namespace = "namespace";
    String bucketName = "bucketName";
    String pictureUrl = osUri + "/n/" + namespace + "/b/" + bucketName + "/o/" + objectName;

    given(courseStateProperties.getIncomplete()).willReturn("incomplete");
    doReturn(incompleteCourse)
        .when(underThisTest)
        .getCourse(anyLong(), anyString(), anyString());

    given(publicObjectStorageConfigProperties.getRegionalObjectStorageUri())
        .willReturn(osUri);
    given(publicObjectStorageConfigProperties.getNamespace())
        .willReturn(namespace);
    given(publicObjectStorageConfigProperties.getBucketName())
        .willReturn(bucketName);

    //when
    Map<String, String> expected = underThisTest.saveCoursePictureUrl(courseId, objectName, username);

    //then
    assertThat(expected.get("pictureUrl")).isEqualTo(pictureUrl);
  }

  @Test
  void givenNullParameters_whenCreateParToUploadCourseVideo_thenThrow() {
    //given
    Long courseId = 1L;
    String username = "username";

    //when
    //then
    assertThatThrownBy(() -> underTest.createParToUploadCourseVideo(courseId, null, null, null, username))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining("must not be null");
  }

  @Test
  void givenNotNullParameters_whenCreateParToUploadCourseVideo_thenReturnParUrl() {
    //given
    InstructorService underThisTest = spy(underTest);
    Long courseId = 1L;
    Long chapterIndex = 1L;
    Long sectionIndex = 1L;
    String fileName = "file";
    String username = "username";
    String videoExtension = "mp4";
    IncompleteCourseResponse incompleteCourseResponse = IncompleteCourseResponse
        .builder()
        .id(courseId)
        .build();
    String videoObjectName = "course_vid_"
        + courseId
        + "_c"
        + chapterIndex
        + "_s"
        + sectionIndex
        + "."
        + videoExtension;
    String parUrl = "test.par.com";

    given(privateObjectStorageConfigProperties.getViableVideoExtensions())
        .willReturn(List.of(videoExtension));
    given(fileExtensionStringHandler.getViableExtension(fileName, List.of(videoExtension)))
        .willReturn(videoExtension);
    doReturn(incompleteCourseResponse)
        .when(underThisTest)
        .getIncompleteCourseDetails(anyLong(), anyString());
    given(privateObjectStorageService.uploadFile(videoObjectName, username))
        .willReturn(parUrl);

    //when
    Map<String, String> expected = underThisTest.createParToUploadCourseVideo(courseId,
        chapterIndex,
        sectionIndex,
        fileName,
        username);

    //then
    assertThat(expected.get("preauthenticatedRequestUrl")).isEqualTo(parUrl);
  }

  @Test
  void givenNullParameters_whenCreateParToUploadHandout_thenThrow() {
    //given
    Long courseId = 1L;
    String username = "username";

    //when
    //then
    assertThatThrownBy(() -> underTest.createParToUploadHandout(courseId, null, null, null, username))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining("must not be null");
  }

  @Test
  void givenNotNullParameters_whenCreateParToUploadHandout_thenThrow() {
    //given
    InstructorService underThisTest = spy(underTest);
    Long courseId = 1L;
    Long chapterIndex = 1L;
    Long sectionIndex = 1L;
    String fileName = "file.txt";
    String username = "username";
    IncompleteCourseResponse incompleteCourseResponse = IncompleteCourseResponse
        .builder()
        .id(courseId)
        .build();
    String handoutObjectName = "course_handout_"
        + courseId
        + "_c"
        + chapterIndex
        + "_s"
        + sectionIndex
        + "_"
        + fileName;
    String parUrl = "test.par.com";

    doReturn(incompleteCourseResponse)
        .when(underThisTest)
        .getIncompleteCourseDetails(anyLong(), anyString());
    given(privateObjectStorageService.uploadFile(handoutObjectName, username))
        .willReturn(parUrl);

    //when
    Map<String, String> expected = underThisTest.createParToUploadHandout(courseId,
        chapterIndex,
        sectionIndex,
        fileName,
        username);

    //then
    assertThat(expected.get("preauthenticatedRequestUrl")).isEqualTo(parUrl);

  }

  @Test
  void givenInvalidObjectName_whenDeleteHandout_thenThrow() {
    //given
    InstructorService underThisTest = spy(underTest);
    Long courseId = 1L;
    String invalidHandoutObjectName = "invalid";
    String username = "username";
    IncompleteCourseResponse incompleteCourseResponse = IncompleteCourseResponse
        .builder()
        .id(courseId)
        .build();

    doReturn(incompleteCourseResponse)
        .when(underThisTest)
        .getIncompleteCourseDetails(anyLong(), anyString());

    //when
    //then
    assertThatThrownBy(() -> underThisTest.deleteHandout(courseId, invalidHandoutObjectName, username))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Object not found");
  }

  @Test
  void givenValidObjectName_whenDeleteHandout_thenDelete() {
    //given
    InstructorService underThisTest = spy(underTest);
    Long courseId = 1L;
    long chapterIndex = 1L;
    long sectionIndex = 1L;
    String fileName = "file.txt";
    String username = "username";
    IncompleteCourseResponse incompleteCourseResponse = IncompleteCourseResponse
        .builder()
        .id(courseId)
        .build();
    String handoutObjectName = "course_handout_"
        + courseId
        + "_c"
        + chapterIndex
        + "_s"
        + sectionIndex
        + "_"
        + fileName;

    doReturn(incompleteCourseResponse)
        .when(underThisTest)
        .getIncompleteCourseDetails(anyLong(), anyString());

    //when
    underThisTest.deleteHandout(courseId, handoutObjectName, username);

    //then
    verify(privateObjectStorageService).deleteFile(anyString());
  }

  @Test
  void givenParameters_whenUpdateCourseMaterials_thenSave() {
    //given
    InstructorService underThisTest = spy(underTest);
    Long courseId = 1L;
    String username = "test@test.com";
    Course incompleteCourse = Course.builder()
        .id(courseId)
        .name("course_name")
        .price(9999L)
        .chapters(List.of(new Chapter()))
        .build();
    Course updatedCourse = Course.builder()
        .chapters(List.of(new Chapter()))
        .build();

    given(courseStateProperties.getIncomplete()).willReturn("incomplete");
    doReturn(incompleteCourse)
        .when(underThisTest)
        .getCourse(anyLong(), anyString(), anyString());

    //when
    underThisTest.updateCourseMaterials(courseId, updatedCourse, username);

    //then
    verify(courseRepo).save(any(Course.class));
  }

  @Test
  void givenIdAndUsername_whenDeleteCoursePictureUrl_thenSave() {
    //given
    InstructorService underThisTest = spy(underTest);
    Long courseId = 1L;
    String username = "test@test.com";
    Course incompleteCourse = Course.builder()
        .id(courseId)
        .name("course_name")
        .price(9999L)
        .chapters(List.of(new Chapter()))
        .build();
    given(courseStateProperties.getIncomplete()).willReturn("incomplete");
    doReturn(incompleteCourse)
        .when(underThisTest)
        .getCourse(anyLong(), anyString(), anyString());

    //when
    underThisTest.deleteCoursePictureUrl(courseId, username);

    //then
    verify(courseRepo).save(any(Course.class));
    assertThat(incompleteCourse.getPictureUrl()).isEqualTo("");
  }

  @Test
  void givenIdAndUsername_whenSubmitIncompleteCourse_thenSave() {
    //given
    InstructorService underThisTest = spy(underTest);
    Long courseId = 1L;
    String username = "test@test.com";
    Course incompleteCourse = Course.builder()
        .id(courseId)
        .name("course_name")
        .price(9999L)
        .chapters(List.of(new Chapter()))
        .build();
    String pending = "pending";
    CourseState pendingState = CourseState.builder()
        .id(1L)
        .name("pending")
        .build();
    given(courseStateProperties.getIncomplete()).willReturn("incomplete");
    doReturn(incompleteCourse)
        .when(underThisTest)
        .getCourse(anyLong(), anyString(), anyString());
    given(courseStateProperties.getPending()).willReturn(pending);
    given(courseStateService.getByName(pending)).willReturn(pendingState);

    //when
    underThisTest.submitIncompleteCourse(courseId, username);

    //then
    verify(courseRepo).save(any(Course.class));
    assertThat(incompleteCourse.getCourseState().getName()).isEqualTo(pending);
  }

  @Test
  void givenIdAndUsername_whenPublishApprovedCourse_thenSave() {
    //given
    InstructorService underThisTest = spy(underTest);
    Long courseId = 1L;
    String username = "test@test.com";
    Course course = Course.builder()
        .id(courseId)
        .name("course_name")
        .price(9999L)
        .chapters(List.of(new Chapter()))
        .build();

    String published = "published";
    CourseState publishedState = CourseState.builder()
        .id(1L)
        .name(published)
        .build();
    given(courseStateProperties.getApproved()).willReturn("approved");
    doReturn(course)
        .when(underThisTest)
        .getCourse(anyLong(), anyString(), anyString());
    given(courseStateProperties.getPublished()).willReturn(published);
    given(courseStateService.getByName(published)).willReturn(publishedState);

    //when
    underThisTest.publishApprovedCourse(courseId, username);

    //then
    assertThat(course.getPublishedCourse()).isNotNull();
    verify(courseRepo).save(any(Course.class));
  }

  @Test
  void givenCourseStateAndInstructor_whenGetAllNonPublishedCourseDTOs_thenReturn() {
    //given
    CourseState pendingState = CourseState.builder()
        .name("pending")
        .build();
    Student student = Student.builder()
        .firstName("First")
        .lastName("Last")
        .build();
    Instructor instructor = Instructor.builder()
        .student(student)
        .build();
    Course course = Course.builder()
        .id(1L)
        .name("Course Name")
        .price(500L)
        .pictureUrl("picture.com")
        .build();

    given(courseRepo.findCoursesByCourseStateAndInstructor(pendingState, instructor))
        .willReturn(List.of(course));

    //when
    HashMap<String, Object> expected = underTest.getAllNonPublishedCourseDTOs(pendingState, instructor);

    //then
    assertThat(expected.get("courses")).isNotNull();
    assertThat(expected.get("instructorFullName")).isNotNull();
  }

  @Test
  void givenUsername_whenGetAllIncompleteCourses_thenReturn() {
    //given
    InstructorService underThisTest = spy(underTest);
    String username = "user";
    Instructor instructor = Instructor.builder().build();
    String incomplete = "incomplete";
    CourseState incompleteState = CourseState.builder().build();
    HashMap<String, Object> dto = new HashMap<>();
    dto.put("courses", new CourseResponse());
    dto.put("instructorFullName", "First Last");

    given(userService.getInstructor(username)).willReturn(instructor);
    given(courseStateProperties.getIncomplete()).willReturn(incomplete);
    given(courseStateService.getByName(incomplete)).willReturn(incompleteState);
    doReturn(dto)
        .when(underThisTest)
        .getAllNonPublishedCourseDTOs(any(CourseState.class), any(Instructor.class));
    //when
    HashMap<String, Object> expected = underThisTest.getAllIncompleteCourses(username);

    //then
    assertThat(expected.get("courses")).isNotNull();
    assertThat(expected.get("instructorFullName")).isNotNull();
  }

  @Test
  void givenUsername_whenGetAllPendingCourses_thenReturn() {
    //given
    InstructorService underThisTest = spy(underTest);
    String username = "user";
    Instructor instructor = Instructor.builder().build();
    String pending = "pending";
    CourseState pendingState = CourseState.builder().build();
    HashMap<String, Object> dto = new HashMap<>();
    dto.put("courses", new CourseResponse());
    dto.put("instructorFullName", "First Last");

    given(userService.getInstructor(username)).willReturn(instructor);
    given(courseStateProperties.getPending()).willReturn(pending);
    given(courseStateService.getByName(pending)).willReturn(pendingState);
    doReturn(dto)
        .when(underThisTest)
        .getAllNonPublishedCourseDTOs(any(CourseState.class), any(Instructor.class));
    //when
    HashMap<String, Object> expected = underThisTest.getAllPendingCourses(username);

    //then
    assertThat(expected.get("courses")).isNotNull();
    assertThat(expected.get("instructorFullName")).isNotNull();
  }

  @Test
  void givenUsername_whenGetAllApprovedCourses_thenReturn() {
    //given
    InstructorService underThisTest = spy(underTest);
    String username = "user";
    Instructor instructor = Instructor.builder().build();
    String approved = "approved";
    CourseState approvedState = CourseState.builder().build();
    HashMap<String, Object> dto = new HashMap<>();
    dto.put("courses", new CourseResponse());
    dto.put("instructorFullName", "First Last");

    given(userService.getInstructor(username)).willReturn(instructor);
    given(courseStateProperties.getApproved()).willReturn(approved);
    given(courseStateService.getByName(approved)).willReturn(approvedState);
    doReturn(dto)
        .when(underThisTest)
        .getAllNonPublishedCourseDTOs(any(CourseState.class), any(Instructor.class));
    //when
    HashMap<String, Object> expected = underThisTest.getAllApprovedCourses(username);

    //then
    assertThat(expected.get("courses")).isNotNull();
    assertThat(expected.get("instructorFullName")).isNotNull();
  }

  @Test
  void givenUsername_whenGetAllRejectedCourses_thenReturn() {
    //given
    InstructorService underThisTest = spy(underTest);
    String username = "user";
    Instructor instructor = Instructor.builder().build();
    String rejected = "rejected";
    CourseState rejectedState = CourseState.builder().build();
    HashMap<String, Object> dto = new HashMap<>();
    dto.put("courses", new CourseResponse());
    dto.put("instructorFullName", "First Last");

    given(userService.getInstructor(username)).willReturn(instructor);
    given(courseStateProperties.getRejected()).willReturn(rejected);
    given(courseStateService.getByName(rejected)).willReturn(rejectedState);
    doReturn(dto)
        .when(underThisTest)
        .getAllNonPublishedCourseDTOs(any(CourseState.class), any(Instructor.class));
    //when
    HashMap<String, Object> expected = underThisTest.getAllRejectedCourses(username);

    //then
    assertThat(expected.get("courses")).isNotNull();
    assertThat(expected.get("instructorFullName")).isNotNull();
  }

  @Test
  void givenUsername_whenGetAllPublishedCourse_thenReturnPublishedCourseInfoResponseList() {
    //given
    String username = "user";
    String published = "published";
    CourseState publishedState = CourseState.builder()
        .name(published)
        .build();
    Instructor instructor = Instructor.builder().build();
    PublishedCourse publishedCourse = PublishedCourse.builder()
        .star(0D)
        .totalUser(0L)
        .build();
    Course course = Course.builder()
        .id(1L)
        .name("Course Name")
        .price(500L)
        .pictureUrl("picture.com")
        .publishedCourse(publishedCourse)
        .build();

    given(userService.getInstructor(username)).willReturn(instructor);
    given(courseStateProperties.getPublished()).willReturn(published);
    given(courseStateService.getByName(published)).willReturn(publishedState);
    given(courseRepo.findCoursesByCourseStateAndInstructor(publishedState, instructor))
        .willReturn(List.of(course));

    //when
    List<PublishedCourseInfoResponse> expected = underTest.getAllPublishedCourses(username);

    //then
    assertThat(expected.get(0).getId()).isEqualTo(course.getId());
    assertThat(expected.get(0).getName()).isEqualTo(course.getName());
    assertThat(expected.get(0).getPrice()).isEqualTo(course.getPrice());
    assertThat(expected.get(0).getPictureUrl()).isEqualTo(course.getPictureUrl());
    assertThat(expected.get(0).getRating()).isEqualTo(course.getPublishedCourse().getStar());
    assertThat(expected.get(0).getReviewTotal()).isEqualTo(course.getPublishedCourse().getTotalUser());
  }
}
package com.ved.backend.service;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.PrivateObjectStorageConfigProperties;
import com.ved.backend.configuration.PublicObjectStorageConfigProperties;
import com.ved.backend.exception.CourseNotFoundException;
import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.exception.baseException.NotFoundException;
import com.ved.backend.model.*;
import com.ved.backend.repo.*;
import com.ved.backend.request.FinanceDataRequest;
import com.ved.backend.response.IncompleteCourseResponse;
import com.ved.backend.response.PublishedCourseInfoResponse;
import com.ved.backend.utility.FileExtensionStringHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class InstructorService {

  private final CourseRepo courseRepo;
  private final InstructorRepo instructorRepo;

  private final UserService userService;
  private final OmiseService omiseService;
  private final CourseStateService courseStateService;
  private final PublicObjectStorageService publicObjectStorageService;
  private final PrivateObjectStorageService privateObjectStorageService;

  private final FileExtensionStringHandler fileExtensionStringHandler;
  private final CourseStateProperties courseStateProperties;
  private final PublicObjectStorageConfigProperties publicObjectStorageConfigProperties;
  private final PrivateObjectStorageConfigProperties privateObjectStorageConfigProperties;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorService.class);

  public String getOmiseAccountData(String username){
    Instructor instructor = userService.getInstructor(username);
    String recipientId = instructor.getRecipientId();
    String response = omiseService.getRecipientData(recipientId);
    return response;
  }

  public String updateFinanceAccount(FinanceDataRequest financeDataRequest, String username){
    Instructor instructor = userService.getInstructor(username);
    String recipientId = instructor.getRecipientId();
    String response = omiseService.updateRecipient(financeDataRequest, recipientId);
    omiseService.verifyRecipient(recipientId); // Mark a recipient as verified
    return response;
  }

  public HashMap<String, Long> createCourse(Course course, String username) {
    Instructor instructor = userService.getInstructor(username);
    CourseState incompleteState = courseStateService.getByName(courseStateProperties.getIncomplete());
    course.setCourseState(incompleteState);
    course.setInstructor(instructor);
    instructor.getCourses().add(course);
    log.info("Creating course from user: {}", username);
    courseRepo.save(course);
    instructorRepo.save(instructor);
    HashMap<String, Long> payload = new HashMap<>();
    payload.put("id", course.getId());
    return payload;
  }

  public Course getCourse(Long courseId, String username, String courseState) {
    Instructor instructor = userService.getInstructor(username);
    CourseState incompleteState = courseStateService.getByName(courseState);
    log.info("Finding {} course from instructor: {}", courseState, username);
    return courseRepo
        .findByInstructorAndCourseStateAndId(instructor, incompleteState, courseId)
        .orElseThrow(() -> new CourseNotFoundException(courseId));

  }

  public IncompleteCourseResponse getIncompleteCourseDetails(Long courseId, String username) {
    Course incompleteCourse = getCourse(courseId, username, courseStateProperties.getIncomplete());
    return IncompleteCourseResponse.builder()
        .id(incompleteCourse.getId())
        .name(incompleteCourse.getName())
        .price(incompleteCourse.getPrice())
        .pictureUrl(incompleteCourse.getPictureUrl())
        .chapters(incompleteCourse.getChapters())
        .build();
  }

  public Map<String, String> createParToUploadCoursePicture(Long courseId, String fileName, String username) {
    String pictureExtension = fileExtensionStringHandler
        .getViableExtension(fileName, publicObjectStorageConfigProperties.getViableImageExtensions());
    IncompleteCourseResponse incompleteCourseResponse = getIncompleteCourseDetails(courseId, username);
    String objectName = "course_pic_" + incompleteCourseResponse.getId() + "." + pictureExtension;
    String preauthenticatedRequestUrl = publicObjectStorageService.uploadFile(objectName, username);
    return Map.of("preauthenticatedRequestUrl", preauthenticatedRequestUrl);
  }

  public Map<String, String> saveCoursePictureUrl(Long courseId, String objectName, String username) {
    if (!objectName.contains("course_pic_" + courseId)) {
      throw new BadRequestException("Invalid Object Name");
    }

    Course incompleteCourse = getCourse(courseId, username, courseStateProperties.getIncomplete());

    log.info("Updating course picture from instructor: {}", username);
    String pictureUrl = publicObjectStorageConfigProperties.getRegionalObjectStorageUri() +
        "/n/" + publicObjectStorageConfigProperties.getNamespace() +
        "/b/" + publicObjectStorageConfigProperties.getBucketName() +
        "/o/" + objectName;
    incompleteCourse.setPictureUrl(pictureUrl);
    courseRepo.save(incompleteCourse);
    return Map.of("pictureUrl", pictureUrl);
  }

  public Map<String, String> createParToUploadCourseVideo(Long courseId,
                                                          Long chapterIndex,
                                                          Long sectionIndex,
                                                          String fileName,
                                                          String username) {
    if (chapterIndex == null || sectionIndex == null || fileName == null) {
      throw new BadRequestException("Request body parameters must not be null.");
    }
    String videoExtension = fileExtensionStringHandler
        .getViableExtension(fileName, privateObjectStorageConfigProperties.getViableVideoExtensions());
    IncompleteCourseResponse incompleteCourseResponse = getIncompleteCourseDetails(courseId, username);
    String videoObjectName = "course_vid_"
        + incompleteCourseResponse.getId()
        + "_c"
        + chapterIndex
        + "_s"
        + sectionIndex
        + "."
        + videoExtension;
    String preauthenticatedRequestUrl = privateObjectStorageService.uploadFile(videoObjectName, username);
    return Map.of("preauthenticatedRequestUrl", preauthenticatedRequestUrl);
  }

  public Map<String, String> createParToReadCourseVideoFromIncompleteCourse(Long courseId,
                                                                            Long chapterIndex,
                                                                            Long sectionIndex,
                                                                            String fileName,
                                                                            String username) {
    if (chapterIndex == null || sectionIndex == null || fileName == null) {
      throw new BadRequestException("Request body parameters must not be null.");
    }
    log.info("Read Video: {} from Course: {} by Instructor: {}", fileName, courseId, username);
    String videoExtension = fileExtensionStringHandler
        .getViableExtension(fileName, privateObjectStorageConfigProperties.getViableVideoExtensions());
    IncompleteCourseResponse incompleteCourseResponse = getIncompleteCourseDetails(courseId, username);
    String videoObjectName = "course_vid_"
        + incompleteCourseResponse.getId()
        + "_c"
        + chapterIndex
        + "_s"
        + sectionIndex
        + "."
        + videoExtension;
    String preauthenticatedRequestUrl = privateObjectStorageService.readFile(videoObjectName, username);
    return Map.of("preauthenticatedRequestUrl", preauthenticatedRequestUrl);
  }

  public Map<String, String> createParToUploadHandout(Long courseId,
                                                      Long chapterIndex,
                                                      Long sectionIndex,
                                                      String fileName,
                                                      String username) {
    if (chapterIndex == null || sectionIndex == null || fileName == null) {
      throw new BadRequestException("Request body parameters must not be null.");
    }
    IncompleteCourseResponse incompleteCourseResponse = getIncompleteCourseDetails(courseId, username);
    String handoutObjectName = "course_handout_"
        + incompleteCourseResponse.getId()
        + "_c"
        + chapterIndex
        + "_s"
        + sectionIndex
        + "_"
        + fileName;
    String preauthenticatedRequestUrl = privateObjectStorageService.uploadFile(handoutObjectName, username);
    return Map.of("preauthenticatedRequestUrl", preauthenticatedRequestUrl);
  }

  public void deleteHandout(Long courseId, String handoutObjectName, String username) {
    IncompleteCourseResponse incompleteCourseResponse = getIncompleteCourseDetails(courseId, username);
    String handoutPrefixName = "course_handout_" + incompleteCourseResponse.getId();
    if (!handoutObjectName.startsWith(handoutPrefixName)) {
      throw new NotFoundException("Object not found");
    }
    privateObjectStorageService.deleteFile(handoutObjectName);
  }

  public void updateCourseMaterials(Long courseId, Course course, String username) {
    Course incompleteCourse = getCourse(courseId, username, courseStateProperties.getIncomplete());

    log.info("Updating course materials from instructor: {}", username);
    incompleteCourse.setChapters(course.getChapters());
    courseRepo.save(incompleteCourse);
  }

  public void deleteCoursePictureUrl(Long courseId, String username) {
    Course incompleteCourse = getCourse(courseId, username, courseStateProperties.getIncomplete());

    log.info("Deleting course picture from instructor: {}", username);
    incompleteCourse.setPictureUrl("");
    courseRepo.save(incompleteCourse);
  }

  public void submitIncompleteCourse(Long courseId, String username) {
    Course incompleteCourse = getCourse(courseId, username, courseStateProperties.getIncomplete());
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());

    log.info("Submitting course from instructor: {}", username);
    incompleteCourse.setCourseState(pendingState);
    courseRepo.save(incompleteCourse);
  }

  public void publishApprovedCourse(Long courseId, String username) {
    Course course = getCourse(courseId, username, courseStateProperties.getApproved());
    CourseState publishedState = courseStateService.getByName(courseStateProperties.getPublished());
    course.setCourseState(publishedState);
    PublishedCourse publishedCourse = PublishedCourse
        .builder()
        .totalScore(0.0)
        .totalUser(0L)
        .star(0.0)
        .course(course)
        .build();
    course.setPublishedCourse(publishedCourse);
    courseRepo.save(course);
  }

  public HashMap<String, Object> getAllNonPublishedCourseDTOs(CourseState courseState,
                                                              Instructor instructor) {
    List<Course> courses = courseRepo.findCoursesByCourseStateAndInstructor(courseState, instructor);
    List<IncompleteCourseResponse> courseResponses = courses
        .stream()
        .map(c -> IncompleteCourseResponse
            .builder()
            .id(c.getId())
            .name(c.getName())
            .price(c.getPrice())
            .pictureUrl(c.getPictureUrl())
            .build())
        .collect(Collectors.toList());
    HashMap<String, Object> dto = new HashMap<>();
    dto.put("courses", courseResponses);
    dto.put("instructorFullName", instructor.getStudent().getFullName());
    return dto;
  }

  public HashMap<String, Object> getAllIncompleteCourses(String username) {
    Instructor instructor = userService.getInstructor(username);
    CourseState incompleteState = courseStateService.getByName(courseStateProperties.getIncomplete());

    log.info("Finding all incomplete courses from instructor: {}", username);
    return getAllNonPublishedCourseDTOs(incompleteState, instructor);
  }

  public HashMap<String, Object> getAllPendingCourses(String username) {
    Instructor instructor = userService.getInstructor(username);
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());

    log.info("Finding all pending courses from instructor: {}", username);
    return getAllNonPublishedCourseDTOs(pendingState, instructor);
  }

  public HashMap<String, Object> getAllApprovedCourses(String username) {
    Instructor instructor = userService.getInstructor(username);
    CourseState approvedState = courseStateService.getByName(courseStateProperties.getApproved());

    log.info("Finding all approved courses from instructor: {}", username);
    return getAllNonPublishedCourseDTOs(approvedState, instructor);
  }

  public HashMap<String, Object> getAllRejectedCourses(String username) {
    Instructor instructor = userService.getInstructor(username);
    CourseState rejectedState = courseStateService.getByName(courseStateProperties.getRejected());

    log.info("Finding all rejected courses from instructor: {}", username);
    return getAllNonPublishedCourseDTOs(rejectedState, instructor);
  }

  public List<PublishedCourseInfoResponse> getAllPublishedCourses(String username) {
    Instructor instructor = userService.getInstructor(username);
    CourseState publishedState = courseStateService.getByName(courseStateProperties.getPublished());

    log.info("Finding all published courses from instructor: {}", username);
    List<Course> courses = courseRepo.findCoursesByCourseStateAndInstructor(publishedState, instructor);
    return courses
        .stream()
        .map(c -> PublishedCourseInfoResponse
            .builder()
            .id(c.getId())
            .name(c.getName())
            .pictureUrl(c.getPictureUrl())
            .price(c.getPrice())
            .rating(c.getPublishedCourse().getStar())
            .reviewTotal(c.getPublishedCourse().getTotalUser())
            .build())
        .collect(Collectors.toList());
  }
}
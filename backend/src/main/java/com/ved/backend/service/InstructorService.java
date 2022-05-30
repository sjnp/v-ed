package com.ved.backend.service;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.PublicObjectStorageConfigProperties;
import com.ved.backend.exception.CourseNotFoundException;
import com.ved.backend.model.*;
import com.ved.backend.repo.*;
import com.ved.backend.response.IncompleteCourseResponse;
import com.ved.backend.utility.FileExtensionStringHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
@Transactional
public class InstructorService {

  private final AppUserRepo appUserRepo;
  private final CourseRepo courseRepo;
  private final CourseStateRepo courseStateRepo;
  private final InstructorRepo instructorRepo;

  private final UserService userService;
  private final CourseStateService courseStateService;
  private final PublicObjectStorageService publicObjectStorageService;

  private final CourseStateProperties courseStateProperties;
  private final PublicObjectStorageConfigProperties publicObjectStorageConfigProperties;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorService.class);

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

  public Course getIncompleteCourse(Long courseId, String username) {
    Instructor instructor = userService.getInstructor(username);
    CourseState incompleteState = courseStateService.getByName(courseStateProperties.getIncomplete());
    log.info("Finding course from instructor: {}", username);
    return courseRepo
        .findByInstructorAndCourseStateAndId(instructor, incompleteState, courseId)
        .orElseThrow(() -> new CourseNotFoundException(courseId));

  }

  public IncompleteCourseResponse getIncompleteCourseDetails(Long courseId, String username) {
    Course incompleteCourse = getIncompleteCourse(courseId, username);
    return IncompleteCourseResponse.builder()
        .id(incompleteCourse.getId())
        .name(incompleteCourse.getName())
        .price(incompleteCourse.getPrice())
        .pictureUrl(incompleteCourse.getPictureUrl())
        .chapters(incompleteCourse.getChapters())
        .build();
  }

  public Map<String, String> createParToUploadCoursePicture(Long courseId, String fileName, String username) {
    String pictureExtension = FileExtensionStringHandler.getViableExtension(fileName,
        publicObjectStorageConfigProperties.getViableImageExtensions());
    IncompleteCourseResponse incompleteCourseResponse = getIncompleteCourseDetails(courseId, username);
    String objectName = "course_pic_" + incompleteCourseResponse.getId() + "." + pictureExtension;
    String preauthenticatedRequestUrl = publicObjectStorageService.uploadFile(objectName, username);
    return Map.of("preauthenticatedRequestUrl", preauthenticatedRequestUrl);
  }

  public Map<String, String> saveCoursePictureUrl(Long courseId, String objectName, String username) {
    Course incompleteCourse = getIncompleteCourse(courseId, username);

    log.info("Updating course picture from instructor: {}", username);
    String pictureUrl = publicObjectStorageConfigProperties.getRegionalObjectStorageUri() +
        "/n/" + publicObjectStorageConfigProperties.getNamespace() +
        "/b/" + publicObjectStorageConfigProperties.getBucketName() +
        "/o/" + objectName;
    incompleteCourse.setPictureUrl(pictureUrl);
    courseRepo.save(incompleteCourse);
    return Map.of("pictureUrl", pictureUrl);
  }

  public void updateCourseMaterials(Long courseId, Course course, String username) {
    Course incompleteCourse = getIncompleteCourse(courseId, username);

    log.info("Updating course materials from instructor: {}", username);
    incompleteCourse.setChapters(course.getChapters());
    courseRepo.save(incompleteCourse);
  }

  public void deleteCoursePictureUrl(Long courseId, String username) {
    Course incompleteCourse = getIncompleteCourse(courseId, username);

    log.info("Deleting course picture from instructor: {}", username);
    incompleteCourse.setPictureUrl("");
    courseRepo.save(incompleteCourse);
  }

  public void submitIncompleteCourse(Long courseId, String username) {
    Course incompleteCourse = getIncompleteCourse(courseId, username);
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());

    log.info("Submitting course from instructor: {}", username);
    incompleteCourse.setCourseState(pendingState);
    courseRepo.save(incompleteCourse);
  }

  // TODO: REFACTOR THIS
  public HashMap<String, Object> getAllIncompleteCourses(String username) {
    log.info("Finding all incomplete courses from instructor: {}", username);
    try {
      Student student = appUserRepo.findByUsername(username).getStudent();
      CourseState incompleteState = courseStateRepo.findByName("INCOMPLETE");
      List<CourseRepo.CourseBasicInfo> courses = courseRepo.findCoursesByInstructorAndCourseState(student.getInstructor(), incompleteState);
      HashMap<String, Object> coursesJson = new HashMap<>();
      coursesJson.put("courses", courses);
      coursesJson.put("instructorFullName", student.getFullName());
      return coursesJson;
    } catch (Exception exception) {
      throw new RuntimeException(exception.getMessage());
    }
  }

  public HashMap<String, Object> getAllPendingCourses(String username) {
    log.info("Finding all pending courses from instructor: {}", username);
    try {
      Student student = appUserRepo.findByUsername(username).getStudent();
      CourseState incompleteState = courseStateRepo.findByName("PENDING");
      List<CourseRepo.CourseBasicInfo> courses = courseRepo.findCoursesByInstructorAndCourseState(student.getInstructor(), incompleteState);
      HashMap<String, Object> coursesJson = new HashMap<>();
      coursesJson.put("courses", courses);
      coursesJson.put("instructorFullName", student.getFullName());
      return coursesJson;
    } catch (Exception exception) {
      throw new RuntimeException(exception.getMessage());
    }
  }

  public HashMap<String, Object> getAllApprovedCourses(String username) {
    log.info("Finding all approved courses from instructor: {}", username);
    try {
      Student student = appUserRepo.findByUsername(username).getStudent();
      CourseState approvedState = courseStateRepo.findByName("APPROVED");
      List<CourseRepo.CourseBasicInfo> courses = courseRepo.findCoursesByInstructorAndCourseState(student.getInstructor(), approvedState);
      HashMap<String, Object> coursesJson = new HashMap<>();
      coursesJson.put("courses", courses);
      coursesJson.put("instructorFullName", student.getFullName());
      return coursesJson;
    } catch (Exception exception) {
      throw new RuntimeException(exception.getMessage());
    }
  }

  public HashMap<String, Object> getAllRejectedCourses(String username) {
    log.info("Finding all rejected courses from instructor: {}", username);
    try {
      Student student = appUserRepo.findByUsername(username).getStudent();
      CourseState rejectedState = courseStateRepo.findByName("REJECTED");
      List<CourseRepo.CourseBasicInfo> courses = courseRepo.findCoursesByInstructorAndCourseState(student.getInstructor(), rejectedState);
      HashMap<String, Object> coursesJson = new HashMap<>();
      coursesJson.put("courses", courses);
      coursesJson.put("instructorFullName", student.getFullName());
      return coursesJson;
    } catch (Exception exception) {
      throw new RuntimeException(exception.getMessage());
    }
  }

  public void publishApprovedCourse(Long courseId, String username) {
    try {
      Instructor instructor = appUserRepo.findByUsername(username).getStudent().getInstructor();
      CourseState approvedState = courseStateRepo.findByName("APPROVED");
      Course course = courseRepo.findCourseByCourseStateAndInstructorAndId(approvedState, instructor, courseId);
      CourseState publishedState = courseStateRepo.findByName("PUBLISHED");
      course.setCourseState(publishedState);
      PublishedCourse publishedCourse = new PublishedCourse();
      publishedCourse.setTotalScore(0.0);
      publishedCourse.setTotalUser(0L);
      publishedCourse.setStar(0.0);
      course.setPublishedCourse(publishedCourse);
      courseRepo.save(course);
    } catch (Exception exception) {
      throw new RuntimeException("Course not found");
    }
  }

  public List<HashMap<String, Object>> getAllPublishedCourses(String username) {
    try {
      Instructor instructor = appUserRepo.findByUsername(username).getStudent().getInstructor();
      CourseState publishedState = courseStateRepo.findByName("PUBLISHED");
      List<Course> publishedCourses = courseRepo.findCoursesByCourseStateAndInstructor(publishedState, instructor);
      List<HashMap<String, Object>> publishedCoursesJson = new ArrayList<>();
      for (Course course : publishedCourses) {
        HashMap<String, Object> courseMap = new HashMap<>();
        courseMap.put("id", course.getId());
        courseMap.put("name", course.getName());
        courseMap.put("pictureUrl", course.getPictureUrl());
        courseMap.put("price", course.getPrice());
        PublishedCourse publishedCourse = course.getPublishedCourse();
        courseMap.put("rating", publishedCourse.getStar());
        courseMap.put("reviewTotal", publishedCourse.getTotalUser());
        publishedCoursesJson.add(courseMap);
      }
      return publishedCoursesJson;
    } catch (Exception exception) {
      throw new RuntimeException("Course not found");
    }
  }

}
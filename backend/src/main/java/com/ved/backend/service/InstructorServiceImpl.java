package com.ved.backend.service;

import com.ved.backend.configuration.PublicObjectStorageConfigProperties;
import com.ved.backend.model.*;
import com.ved.backend.repo.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

  private final AppUserRepo appUserRepo;
  private final CourseRepo courseRepo;
  private final CourseStateRepo courseStateRepo;
  private final InstructorRepo instructorRepo;
  private final PublicObjectStorageConfigProperties publicObjectStorageConfigProperties;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorServiceImpl.class);

  @Override
  public Long createCourse(Course course, String username) {
    log.info("Creating course from instructor: {}", username);
    Instructor instructor = appUserRepo.findByUsername(username).getStudent().getInstructor();
    course.setCourseState(courseStateRepo.findByName("INCOMPLETE"));
    course.setInstructor(instructor);
    instructor.getCourses().add(course);
    courseRepo.save(course);
    instructorRepo.save(instructor);
    return course.getId();
  }

  @Override
  public CourseRepo.CourseMaterials getIncompleteCourse(Long courseId, String username) {
    log.info("Finding course from instructor: {}", username);
    Instructor instructor = appUserRepo.findByUsername(username).getStudent().getInstructor();
    CourseState incompleteState = courseStateRepo.findByName("INCOMPLETE");
    CourseRepo.CourseMaterials incompleteCourseMaterials = courseRepo.findCourseByInstructorAndCourseStateAndId(instructor, incompleteState, courseId);
    if (incompleteCourseMaterials == null) {
      throw new RuntimeException("Course not found");
    }

    return incompleteCourseMaterials;
  }

  @Override
  public String saveCoursePictureUrl(Long courseId, String objectName, String username) {
    log.info("Updating course picture from instructor: {}", username);
    Instructor instructor = appUserRepo.findByUsername(username).getStudent().getInstructor();
    CourseState incompleteState = courseStateRepo.findByName("INCOMPLETE");
    if (courseRepo.findCourseByInstructorAndCourseStateAndId(instructor, incompleteState, courseId) == null) {
      throw new RuntimeException("Course not found");
    }
    String pictureUrl = publicObjectStorageConfigProperties.getRegionalObjectStorageUri() +
        "/n/" + publicObjectStorageConfigProperties.getNamespace() +
        "/b/" + publicObjectStorageConfigProperties.getBucketName() +
        "/o/" + objectName;
    Course incompleteCourse = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    incompleteCourse.setPictureUrl(pictureUrl);
    courseRepo.save(incompleteCourse);
    return pictureUrl;
  }

  @Override
  public void updateCourseMaterials(Long courseId, Course course, String username) {
    log.info("Updating course materials from instructor: {}", username);
    Instructor instructor = appUserRepo.findByUsername(username).getStudent().getInstructor();
    CourseState incompleteState = courseStateRepo.findByName("INCOMPLETE");
    if (courseRepo.findCourseByInstructorAndCourseStateAndId(instructor, incompleteState, courseId) == null) {
      throw new RuntimeException("Course not found");
    }
    Course incompleteCourse = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    incompleteCourse.setChapters(course.getChapters());
    courseRepo.save(incompleteCourse);
  }

  @Override
  public void deleteCoursePictureUrl(Long courseId, String username) {
    log.info("Deleting course picture from instructor: {}", username);
    Instructor instructor = appUserRepo.findByUsername(username).getStudent().getInstructor();
    CourseState incompleteState = courseStateRepo.findByName("INCOMPLETE");
    if (courseRepo.findCourseByInstructorAndCourseStateAndId(instructor, incompleteState, courseId) == null) {
      throw new RuntimeException("Course not found");
    }
    Course incompleteCourse = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    incompleteCourse.setPictureUrl("");
    courseRepo.save(incompleteCourse);
  }

  @Override
  public void submitIncompleteCourse(Long courseId, String username) {
    log.info("Submitting course from instructor: {}", username);
    Instructor instructor = appUserRepo.findByUsername(username).getStudent().getInstructor();
    CourseState incompleteState = courseStateRepo.findByName("INCOMPLETE");
    if (courseRepo.findCourseByInstructorAndCourseStateAndId(instructor, incompleteState, courseId) == null) {
      throw new RuntimeException("Course not found");
    }
    Course incompleteCourse = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
    incompleteCourse.setCourseState(courseStateRepo.findByName("PENDING"));
    courseRepo.save(incompleteCourse);
  }

  @Override
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

  @Override
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


  public InstructorServiceImpl(AppUserRepo appUserRepo, CourseRepo courseRepo, CourseStateRepo courseStateRepo, InstructorRepo instructorRepo, PublicObjectStorageConfigProperties publicObjectStorageConfigProperties) {
    this.appUserRepo = appUserRepo;
    this.courseRepo = courseRepo;
    this.courseStateRepo = courseStateRepo;
    this.instructorRepo = instructorRepo;
    this.publicObjectStorageConfigProperties = publicObjectStorageConfigProperties;
  }

}
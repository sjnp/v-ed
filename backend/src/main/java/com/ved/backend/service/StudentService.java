package com.ved.backend.service;

import com.ved.backend.exception.baseException.UnauthorizedException;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Course;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.StudentRepo;
import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.response.CourseResponse;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class StudentService {

  private final AppUserRepo appUserRepo;
  private final AppRoleRepo appRoleRepo;
  private final StudentRepo studentRepo;

  private final UserService userService;
  private final CourseService courseService;
  private final StudentCourseService studentCourseService;

  private static final Logger log = LoggerFactory.getLogger(StudentService.class);

  /* ************************************************************ */

  public void buyFreeCourse(Long courseId, String username) {
    log.info("Username {} get free course id {}", username, courseId);
    Course course = courseService.getByIdAndPrice(courseId, 0L);
    Student student = userService.getStudent(username);
    studentCourseService.verifyCanBuyCourse(student, course);
    StudentCourse studentCourse = StudentCourse.builder()
      .student(student)
      .course(course)
      .build();
    studentCourseService.save(studentCourse);
  }

  public List<CourseCardResponse> getMyCourse(String username) {
    log.info("Get my course username {}", username);
    Student student = userService.getStudent(username);
    return studentCourseService.getByStudent(student)
      .stream()
      .map((myCourse) -> new CourseCardResponse(myCourse.getCourse()))
      .collect(Collectors.toList());
  }

  @Transactional
  public CourseResponse getCourse(Long courseId, String username) {
    Student student = userService.getStudent(username);
    Course course = courseService.getById(courseId);
    StudentCourse studentCourse = studentCourseService.getByStudentAndCourse(student, course);
    return new CourseResponse(studentCourse.getCourse());
  }

  /* ************************************************************ */

  public Student getStudent(String username) {
    AppUser appUser = userService.getAppUser(username);
    log.info("Fetching student from user: {}", username);
    return studentRepo.findByAppUser(appUser)
        .orElseThrow(() -> {
          String userIsNotStudent = "User with username: " + username + " is not a student";
          log.error(userIsNotStudent);
          return new UnauthorizedException(userIsNotStudent);
        });
  }

  public void changeRoleFromStudentIntoInstructor(Instructor instructor, String username) {
    log.info("Changing role to student: {}", username);
    AppUser appUser = appUserRepo.findByUsername(username);
    List<String> appUserRoles = appUser.getAppRoles().stream()
        .map(AppRole::getName)
        .collect(Collectors.toList());
    if (appUserRoles.contains("INSTRUCTOR")) {
      log.info("Fail, user: {} already is an instructor", username);
    } else if (appUserRoles.contains("STUDENT")) {
      AppRole instructorRole = appRoleRepo.findByName("INSTRUCTOR");
      appUser.getAppRoles().add(instructorRole);
      Student student = appUser.getStudent();
      student.setInstructor(instructor);
      studentRepo.save(student);
      log.info("Success, user: {} is now an instructor", username);
    }
  }

}
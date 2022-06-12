package com.ved.backend.service;

import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Course;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Post;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.StudentRepo;
import com.ved.backend.request.PostRequest;
import com.ved.backend.response.CreatePostResponse;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
  private final PostService postService;

  private static final Logger log = LoggerFactory.getLogger(StudentService.class);
  
  /* ************************************************************************************************** */

  @Transactional
  public StudentCourse authStudentCourse(String username, Long courseId) {
    Student student = userService.getStudent(username);
    Course course = courseService.getById(courseId);
    return studentCourseService.getByStudentAndCourse(student, course);
  }

  public CreatePostResponse createPost(PostRequest postRequest, String username) {
    if (Objects.isNull(postRequest.getCourseId())) {
      throw new BadRequestException("Course id is required");
    }

    if (Objects.isNull(postRequest.getTopic())) {
        throw new BadRequestException("Topic is required");
    }

    if (Objects.isNull(postRequest.getDetail())) {
        throw new BadRequestException("Detail is required");
    }

    StudentCourse studentCourse = this.authStudentCourse(username, postRequest.getCourseId());

    Post post = Post.builder()
      .course(studentCourse.getCourse())
      .studentCourse(studentCourse)
      .topic(postRequest.getTopic())
      .detail(postRequest.getDetail())
      .createDateTime(LocalDateTime.now())
      .visible(true)
      .build();

    Post resultPost = postService.savePost(post);
    return CreatePostResponse.builder()
      .postId(resultPost.getId())
      .build();
  }

  // ----------------------------------------------------------------------------------------------------

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
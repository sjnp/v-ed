package com.ved.backend.service;

import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.exception.baseException.UnauthorizedException;
import com.ved.backend.model.Answer;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Course;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.StudentRepo;
<<<<<<< HEAD
import com.ved.backend.request.ChargeDataRequest;
import com.ved.backend.request.FinanceDataRequest;
import com.ved.backend.response.ChargeResponse;
=======
import com.ved.backend.request.AnswerRequest;
import com.ved.backend.response.AnswerResponse;
import com.ved.backend.response.AssignmentAnswerResponse;
>>>>>>> 8c09a69fa05ea1f265e6d2b2d05f083a6c5b186c
import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.response.CourseResponse;
import com.ved.backend.response.VideoResponse;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class StudentService {

  private final AppUserRepo appUserRepo;
  private final AppRoleRepo appRoleRepo;
  private final StudentRepo studentRepo;
  private final OmiseService omiseService;

  private final UserService userService;
  private final CourseService courseService;
  private final StudentCourseService studentCourseService;
  private final AssignmentService assignmentService;
  private final PrivateObjectStorageService privateObjectStorageService;

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
  public ChargeResponse buyCourse(ChargeDataRequest chargeData, String username) {
    log.info("Username {} get free course id {}", username, chargeData.getCourseId());
    Course course = courseService.getById(chargeData.getCourseId()); // เช็คคอส เช็คฟรี
    Student student = userService.getStudent(username);
    studentCourseService.verifyCanBuyCourse(student, course); // เช็คซื้อคอสได้ไหม ถ้ามีแล้วเออเร่อ
    String sourceId = omiseService.createPaymentSource(chargeData);
    ChargeResponse chargeResponse = omiseService.createPaymentCharge(chargeData,sourceId);
//    StudentCourse studentCourse = StudentCourse.builder() // สร้าง
//            .student(student)
//            .course(course)
//            .build();
//    studentCourseService.save(studentCourse); // เซฟ
    return chargeResponse;
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
  public StudentCourse authStudentCourse(String username, Long courseId) {
    Student student = userService.getStudent(username);
    Course course = courseService.getById(courseId);
    return studentCourseService.getByStudentAndCourse(student, course);
  }

  @Transactional
  public CourseResponse getCourse(Long courseId, String username) {
    StudentCourse studentCourse = this.authStudentCourse(username, courseId);
    return new CourseResponse(studentCourse.getCourse());
  }

  @Transactional
  public VideoResponse getVideoCourseUrl(Long courseId, int chapterIndex, int sectionIndex, String username) {
    StudentCourse studentCourse = this.authStudentCourse(username, courseId);
    String fileName = "course_vid_" + courseId + "_c" + chapterIndex + "_s" + sectionIndex + ".mp4";
    return VideoResponse.builder()
      .videoUrl(privateObjectStorageService.readFile(fileName, username))
      .pictureUrl(studentCourse.getCourse().getPictureUrl())
      .chapterName(studentCourse.getCourse().getChapters().get(chapterIndex).getName())
      .sectionName(studentCourse.getCourse().getChapters().get(chapterIndex).getSections().get(sectionIndex).get("name").toString())
      .build();
  }

  @Transactional
  public String getHandoutUrl(Long courseId, int chapterIndex, int sectionIndex, int handoutIndex, String username) {
    StudentCourse studentCourse = this.authStudentCourse(username, courseId);
    @SuppressWarnings("unchecked")
    List<Map<String, String>> handouts = (List<Map<String, String>>) studentCourse.getCourse()
      .getChapters()
      .get(chapterIndex)
      .getSections()
      .get(sectionIndex)
      .get("handouts");
    String fileName = handouts.get(handoutIndex).get("handoutUri");
    return privateObjectStorageService.readFile(fileName, username);
  }

  public String getUploadAnswerUrl(Long courseId, int chapterIndex, int noIndex, String clientFileName, String username) {
    StudentCourse studentCourse = this.authStudentCourse(username, courseId);
    String fileName = "answer_sid_" + studentCourse.getStudent().getId() + "_cid_" + courseId + "_c" + chapterIndex + "_no." + noIndex + "_" + clientFileName;
    return privateObjectStorageService.uploadFile(fileName, username);
  }

  @Transactional
  public void createAnswer(AnswerRequest answerRequest, String username) {
    
    if (Objects.isNull(answerRequest.getChapterIndex())) {
      throw new BadRequestException("Chapter index is required");
    }

    if (Objects.isNull(answerRequest.getNoIndex())) {
      throw new BadRequestException("No index is required");
    }

    if (Objects.isNull(answerRequest.getFileName())) {
      throw new BadRequestException("File name is required");
    }

    StudentCourse studentCourse = this.authStudentCourse(username, answerRequest.getCourseId());
    Answer answer = Answer.builder()
      .chapterIndex(answerRequest.getChapterIndex())
      .noIndex(answerRequest.getNoIndex())
      .fileName(answerRequest.getFileName())
      .datetime(LocalDateTime.now())
      .studentCourse(studentCourse)
      .build();
    assignmentService.saveAnswer(answer);
  }

  public List<AssignmentAnswerResponse> getAssignmentAnswer(Long courseId, Integer chapterIndex, String username) {
    if (Objects.isNull(chapterIndex)) {
      throw new BadRequestException("Chapter index is required");
    }
    
    StudentCourse studentCourse = this.authStudentCourse(username, courseId);

    List<AssignmentAnswerResponse> assignmentAnswerResponses = studentCourse.getCourse()
      .getChapters()
      .get(chapterIndex)
      .getAssignments()
      .stream()
      .map(assignment -> AssignmentAnswerResponse.builder().assignment(assignment.get("detail")).build())
      .collect(Collectors.toList());

    List<Answer> answers = studentCourse.getAnswers()
      .stream()
      .filter(answer -> answer.getChapterIndex() == chapterIndex)
      .collect(Collectors.toList());

    for(int i = 0; i < assignmentAnswerResponses.size(); ++i) {
      for(Answer answer : answers) {
        if (answer.getNoIndex() == i) {
          assignmentAnswerResponses.get(i).setAnswer(new AnswerResponse(answer));
          break;
        }
      }
    }

    return assignmentAnswerResponses;
  }

  /* ************************************************************************************************** */

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

  public String activeInstructor(FinanceDataRequest finance, String username) {
    try {
      AppUser appUser = appUserRepo.findByUsername(username);
      log.info(username);
      List<String> appUserRoles = appUser.getAppRoles().stream()
              .map(AppRole::getName)
              .collect(Collectors.toList());
      if (appUserRoles.contains("INSTRUCTOR")) {
        log.info("Fail, user: {} already is an instructor", username);
        return "Fail, user: {} already is an instructor" + username;
      } else if (appUserRoles.contains("STUDENT")) {

        String recipientId = omiseService.createRecipient(finance); // Add a recipient with a bank account
        omiseService.verifyRecipient(recipientId); // Mark a recipient as verified

        // Add INSTRUCTOR role to User
        AppRole instructorRole = appRoleRepo.findByName("INSTRUCTOR");
        appUser.getAppRoles().add(instructorRole);
        Student student = appUser.getStudent();
        Instructor instructor = new Instructor();
        instructor.setRecipientId(recipientId);
        student.setInstructor(instructor);
        studentRepo.save(student);
        log.info("Success, user: {} is now an instructor", username);
     }

      return String.valueOf("OK");
    }
    catch (Exception error) {
      System.out.println(error.getMessage());
      return error.getMessage();
    }
  }
  
}
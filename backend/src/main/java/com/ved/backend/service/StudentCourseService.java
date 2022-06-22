package com.ved.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.StudentCourseRepo;
import com.ved.backend.request.ChargeDataRequest;
import com.ved.backend.response.ChargeResponse;
import com.ved.backend.response.CourseCardResponse;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class StudentCourseService {

    private final UserService userService;

    private final CourseRepo courseRepo;
    private final StudentCourseRepo studentCourseRepo;
    private final OmiseService omiseService;

    private static final Logger log = LoggerFactory.getLogger(StudentCourseService.class);

    public void buyFreeCourse(String username, Long courseId) {
        log.info("username: {} buy free course id: {}", username, courseId);
        Student student = userService.getStudent(username);
        Course course = courseRepo
            .findByIdAndPrice(courseId, 0L)
            .orElseThrow(() -> new BadRequestException("This course not free"));
        boolean isExists = studentCourseRepo.existsByStudentAndCourse(student, course);
        if (isExists) {
            throw new ConflictException("You have this course already");
        }
        StudentCourse studentCourse = StudentCourse.builder()
            .student(student)
            .course(course)
            .build();
        studentCourseRepo.save(studentCourse);
    }

    public ChargeResponse buyCourse(ChargeDataRequest chargeData, String username) {
        log.info("Username {} get free course id {}", username, chargeData.getCourseId());
        // Course course = courseService.getById(chargeData.getCourseId()); // เช็คคอส เช็คฟรี
        Course course = courseRepo
            .findById(chargeData.getCourseId())
            .orElseThrow(() -> new BadRequestException("This course not free"));
        Student student = userService.getStudent(username);
        // studentCourseService.verifyCanBuyCourse(student, course); // เช็คซื้อคอสได้ไหม ถ้ามีแล้วเออเร่อ
        boolean isExists = studentCourseRepo.existsByStudentAndCourse(student, course);
        if (isExists) {
            throw new ConflictException("You have this course already");
        }
        String sourceId = omiseService.createPaymentSource(chargeData);
        ChargeResponse chargeResponse = omiseService.createPaymentCharge(chargeData,sourceId);
    //    StudentCourse studentCourse = StudentCourse.builder() // สร้าง
    //            .student(student)
    //            .course(course)
    //            .build();
    //    studentCourseService.save(studentCourse); // เซฟ
        return chargeResponse;
      }

    public List<CourseCardResponse> getMyCourses(String username) {
        log.info("Get my courses of username: {}", username);
        Student student = userService.getStudent(username);
        return studentCourseRepo
            .findByStudent(student)
            .stream()
            .map((myCourse) -> new CourseCardResponse(myCourse.getCourse()))
            .collect(Collectors.toList());
    }

}
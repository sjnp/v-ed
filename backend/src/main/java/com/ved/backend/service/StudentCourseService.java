package com.ved.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.exception.baseException.UnauthorizedException;
import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.*;
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
    private final CourseService courseService;

    private final StudentCourseRepo studentCourseRepo;

    private static final Logger log = LoggerFactory.getLogger(StudentCourseService.class);


    public List<CourseCardResponse> getMyCourses(String username) {
        Student student = userService.getStudent(username);
        return studentCourseRepo
            .findByStudent(student)
            .stream()
            .map((myCourse) -> new CourseCardResponse(myCourse.getCourse()))
            .collect(Collectors.toList());
    }

    //////////////////////

    public void buyFreeCourse(Long courseId, String username) {
        Course course = courseService.getByIdAndPrice(courseId, 0L);
        Student student = userService.getStudent(username);
        Boolean isExists = studentCourseRepo.existsByStudentAndCourse(student, course);
        if (isExists) {
            throw new ConflictException("You have this course already");
        }

        StudentCourse studentCourse = StudentCourse.builder()
            .student(student)
            .course(course)
            .build();
            
        studentCourseRepo.save(studentCourse);
    }


    /////////////////////////

    public Boolean existsByStudentAndCourse(Student student, Course course) {
        return studentCourseRepo.existsByStudentAndCourse(student, course);
    }

    public void verifyCanBuyCourse(Student student, Course course) {
        log.info("Veriry student id {} and course id {}", student.getId(), course.getId());
        if (this.existsByStudentAndCourse(student, course)) {
            throw new ConflictException("You have this course already");
        }
    }

    public StudentCourse save(StudentCourse studentCourse) {
        log.info("Save student id {} and course id {}", studentCourse.getStudent().getId(), studentCourse.getCourse().getId());
        return studentCourseRepo.save(studentCourse);
    }

    public List<StudentCourse> getByStudent(Student student) {
        log.info("Get my course of student id {}", student.getId());
        return studentCourseRepo.findByStudent(student);
    }

    public StudentCourse getByStudentAndCourse(Student student, Course course) {
        log.info("Student id {} get course id {}", student.getId(), course.getId());
        return studentCourseRepo.findByStudentAndCourse(student, course)
            .orElseThrow(() -> new UnauthorizedException("You have not authorized this course"));
    }

}
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
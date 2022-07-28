package com.ved.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ved.backend.exception.CourseNotFoundException;
import com.ved.backend.exception.baseException.UnauthorizedException;
import com.ved.backend.model.Course;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.StudentCourseRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthService {
 
    private final UserService userService;

    private final CourseRepo courseRepo;
    private final StudentCourseRepo studentCourseRepo;

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public StudentCourse authorized(String username, Long courseId) {
        log.info("Auth username {} and course id {}", username, courseId);
        Student student = userService.getStudent(username);
        Course course = courseRepo
            .findById(courseId)
            .orElseThrow(() -> new CourseNotFoundException(courseId));
        return studentCourseRepo
            .findByStudentAndCourse(student, course)
            .orElseThrow(() -> new UnauthorizedException("You are not authorized in this course"));
    }

    public Course authorizedInstructor(String username, Long courseId) {
        Instructor instructor = userService.getInstructor(username);
        return courseRepo
            .findCourseByIdAndInstructor(courseId, instructor)
            .orElseThrow(() -> new UnauthorizedException("You are not owner this course"));
    }

}
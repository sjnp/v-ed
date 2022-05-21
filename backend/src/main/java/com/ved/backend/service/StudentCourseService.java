package com.ved.backend.service;

import java.util.Optional;

import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.*;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class StudentCourseService {

    private final StudentCourseRepo studentCourseRepo;

    private static final Logger log = LoggerFactory.getLogger(StudentCourseService.class);

    public void verifyCanBuyCourse(Student student, Course course) {
        log.info("Veriry student id {} and course id {}", student.getId(), course.getId());
        Optional<StudentCourse> studentCourseOpt = studentCourseRepo.findByStudentAndCourse(student, course);
        if (studentCourseOpt.isPresent()) {
            throw new ConflictException("You have this course already");
        }
    }

    public StudentCourse save(StudentCourse studentCourse) {
        log.info("Save student id {} and course id {}", studentCourse.getStudent().getId(), studentCourse.getCourse().getId());
        return studentCourseRepo.save(studentCourse);
    }

}
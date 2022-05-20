package com.ved.backend.service;

import java.util.Optional;
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

    public Boolean isStudentCourse(Student student, Course course) {
        log.info("Is student id {} and course id {}", student.getId(), course.getId());
        Optional<StudentCourse> scOptional = studentCourseRepo.findByStudentAndCourse(student, course);
        return scOptional.isPresent();
    }

}
package com.ved.backend.repo;

import java.util.Optional;

import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepo extends JpaRepository<StudentCourse, Long> {

    Optional<StudentCourse> findByStudentAndCourse(Student student, Course course);

}
package com.ved.backend.repo;

import com.ved.backend.model.StudentCourse;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepo extends JpaRepository<StudentCourse, Long> {

    public StudentCourse findByCourseIdAndStudentId(Long courseId, Long studentId);
    
}
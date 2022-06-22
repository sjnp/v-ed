package com.ved.backend.repo;

import com.ved.backend.model.PublishedCourse;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishedCourseRepo extends JpaRepository<PublishedCourse, Long> {

    Optional<PublishedCourse> findByCourseId(Long courseId);
    
}
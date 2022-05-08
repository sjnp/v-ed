package com.ved.backend.repo;

import com.ved.backend.model.PublishedCourse;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishedCourseRepo extends JpaRepository<PublishedCourse, Long> {
    
}
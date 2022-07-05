package com.ved.backend.repo;

import com.ved.backend.model.PublishedCourse;
import com.ved.backend.model.Review;
import com.ved.backend.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    
    Boolean existsByStudentAndPublishedCourse(Student student, PublishedCourse publishedCourse);

}
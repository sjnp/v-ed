package com.ved.backend.repo;

import com.ved.backend.model.Review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    
    public Review findByStudentId(Long studentId);

}
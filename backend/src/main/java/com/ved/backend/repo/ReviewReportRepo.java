package com.ved.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ved.backend.model.Review;
import com.ved.backend.model.ReviewReport;
import com.ved.backend.model.Student;

public interface ReviewReportRepo extends JpaRepository<ReviewReport, Long> {
    
    Boolean existsByReviewAndStudent(Review review, Student student);

}
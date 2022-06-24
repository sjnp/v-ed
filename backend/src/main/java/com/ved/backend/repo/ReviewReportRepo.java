package com.ved.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ved.backend.model.ReviewReport;

public interface ReviewReportRepo extends JpaRepository<ReviewReport, Long> {
    
}
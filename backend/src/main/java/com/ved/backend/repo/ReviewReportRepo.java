package com.ved.backend.repo;

import com.ved.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewReportRepo extends JpaRepository<ReviewReport, Long> {

    Boolean existsByReviewAndStudentAndReasonReport(Review review, Student student, ReasonReport reasonReport);
    List<ReviewReport> findAllByReportState(ReportState reportState);
    List<ReviewReport> findAllByReportStateAndReview(ReportState reportState, Review review);
    Optional<ReviewReport> findByIdAndReportState(Long id, ReportState reportState);
}
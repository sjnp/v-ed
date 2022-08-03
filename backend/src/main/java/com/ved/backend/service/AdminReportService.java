package com.ved.backend.service;

import com.ved.backend.configuration.ReportStateProperties;
import com.ved.backend.exception.baseException.NotFoundException;
import com.ved.backend.model.ReportState;
import com.ved.backend.model.Review;
import com.ved.backend.model.ReviewReport;
import com.ved.backend.repo.ReportStateRepo;
import com.ved.backend.repo.ReviewReportRepo;
import com.ved.backend.response.PendingReviewReportResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminReportService {
  private static final Logger log = LoggerFactory.getLogger(AdminService.class);

  private final ReportStateRepo reportStateRepo;
  private final ReviewReportRepo reviewReportRepo;

  private final ReportStateProperties reportStateProperties;

  public List<PendingReviewReportResponse> getAllPendingReviewReports(String username) {
    log.info("Finding all pending review reports, admin: {}", username);
    ReportState pendingState = reportStateRepo.findByName(reportStateProperties.getPending());
    List<ReviewReport> pendingReviewReports = reviewReportRepo.findAllByReportState(pendingState);
    return pendingReviewReports
        .stream()
        .map(r -> PendingReviewReportResponse.builder()
            .id(r.getId())
            .reviewComment(r.getReview().getComment())
            .reportReason(r.getReasonReport().getDescription())
            .studentId(r.getStudent().getId())
            .reporterName(r.getStudent().getFirstName())
            .build())
        .collect(Collectors.toList());
  }

  public void changePendingReviewReportState(Long reviewReportId, Boolean isApproved, String name) {
    ReportState pendingState = reportStateRepo.findByName(reportStateProperties.getPending());
    ReviewReport pendingReviewReport =  reviewReportRepo
        .findByIdAndReportState(reviewReportId, pendingState)
        .orElseThrow(() -> new NotFoundException("Review report not found"));
    ReportState newReportState;

    if (isApproved) {
      newReportState = reportStateRepo.findByName(reportStateProperties.getApproved());
      List<ReviewReport> pendingReviewReports = reviewReportRepo
          .findAllByReportStateAndReview(pendingState, pendingReviewReport.getReview());
      pendingReviewReports.stream().forEach(r -> {
        r.setReportState(newReportState);
        reviewReportRepo.save(r);
      });
      pendingReviewReport.getReview().setVisible(false);
    } else {
      newReportState = reportStateRepo.findByName(reportStateProperties.getRejected());
    }

    pendingReviewReport.setReportState(newReportState);
    reviewReportRepo.save(pendingReviewReport);
  }
}

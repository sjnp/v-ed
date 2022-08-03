package com.ved.backend.service;

import com.ved.backend.configuration.ReportStateProperties;
import com.ved.backend.exception.baseException.NotFoundException;
import com.ved.backend.model.*;
import com.ved.backend.repo.PostReportRepo;
import com.ved.backend.repo.ReportStateRepo;
import com.ved.backend.repo.ReviewReportRepo;
import com.ved.backend.response.PendingPostReportResponse;
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
  private final PostReportRepo postReportRepo;

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

  public void changePendingReviewReportState(Long reviewReportId, Boolean isApproved, String username) {
    log.info("Change pending review report, admin: {}", username);
    ReportState pendingState = reportStateRepo.findByName(reportStateProperties.getPending());
    ReviewReport pendingReviewReport = reviewReportRepo
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

  public List<PendingPostReportResponse> getAllPendingPostReports(String username) {
    log.info("Finding all pending post reports, admin: {}", username);
    ReportState pendingState = reportStateRepo.findByName(reportStateProperties.getPending());
    List<PostReport> pendingPostReports = postReportRepo.findAllByReportState(pendingState);
    return pendingPostReports
        .stream()
        .map(r -> PendingPostReportResponse.builder()
            .id(r.getId())
            .postTopic(r.getPost().getTopic())
            .postDetail(r.getPost().getDetail())
            .reportReason(r.getReasonReport().getDescription())
            .studentId(r.getStudent().getId())
            .reporterName(r.getStudent().getFirstName())
            .build())
        .collect(Collectors.toList());
  }

  public void changePendingPostReportState(Long postReportId, Boolean isApproved, String username) {
    log.info("Change pending post report, admin: {}", username);
    ReportState pendingState = reportStateRepo.findByName(reportStateProperties.getPending());
    PostReport pendingPostReport = postReportRepo
        .findByIdAndReportState(postReportId, pendingState)
        .orElseThrow(() -> new NotFoundException("Post report not found"));
    ReportState newReportState;

    if (isApproved) {
      newReportState = reportStateRepo.findByName(reportStateProperties.getApproved());
      List<PostReport> pendingPostReports = postReportRepo
          .findAllByReportStateAndPost(pendingState, pendingPostReport.getPost());
      pendingPostReports.stream().forEach(r -> {
        r.setReportState(newReportState);
        postReportRepo.save(r);
      });
      pendingPostReport.getPost().setVisible(false);
    } else {
      newReportState = reportStateRepo.findByName(reportStateProperties.getRejected());
    }

    pendingPostReport.setReportState(newReportState);
    postReportRepo.save(pendingPostReport);
  }
}

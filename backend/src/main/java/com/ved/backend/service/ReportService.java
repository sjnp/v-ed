package com.ved.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ved.backend.configuration.ReportStateProperties;
import com.ved.backend.exception.PostNotFoundException;
import com.ved.backend.exception.ReasonReportNotFoundException;
import com.ved.backend.exception.ReviewNotFoundException;
import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.model.Post;
import com.ved.backend.model.PostReport;
import com.ved.backend.model.ReasonReport;
import com.ved.backend.model.ReportState;
import com.ved.backend.model.Review;
import com.ved.backend.model.ReviewReport;
import com.ved.backend.model.Student;
import com.ved.backend.repo.PostRepo;
import com.ved.backend.repo.PostReportRepo;
import com.ved.backend.repo.ReasonReportRepo;
import com.ved.backend.repo.ReportStateRepo;
import com.ved.backend.repo.ReviewRepo;
import com.ved.backend.repo.ReviewReportRepo;
import com.ved.backend.request.ReportRequest;
import com.ved.backend.response.ReasonReportResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReportService {

    private final UserService userService;

    private final ReasonReportRepo reasonReportRepo;
    
    private final ReviewRepo reviewRepo;
    private final ReviewReportRepo reviewReportRepo;
    private final ReportStateRepo reportStateRepo;

    private final PostRepo postRepo;
    private final PostReportRepo postReportRepo;

    private final ReportStateProperties reportStateProperties;

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    public List<ReasonReportResponse> getReasonReports() {
        return reasonReportRepo
            .findAll()
            .stream()
            .map(reasonReport -> new ReasonReportResponse(reasonReport))
            .collect(Collectors.toList());
    }

    public void createReport(String username, ReportRequest reportRequest) {
        log.info("username: {} report {} id {}",username, reportRequest.getReportType(), reportRequest.getContentId());

        if (reportRequest.getContentId().equals(null)) {
            throw new BadRequestException("Content id is required");
        }

        if (reportRequest.getReasonReportId().equals(null)) {
            throw new BadRequestException("Reason report id is required");
        }

        if (reportRequest.getReportType().equals(null)) {
            throw new BadRequestException("Report type is required");
        }

        switch (reportRequest.getReportType()) {
            case "review":
                this.reportReview(username, reportRequest);
                break;
            case "post":
                this.reportPost(username, reportRequest);
                break;
            case "comment":
                this.reportComment(username, reportRequest);
                break;
            default:
                throw new BadRequestException("Report type invalid");
        }
    }

    public void reportReview(String username, ReportRequest reportRequest) {
        Student student = userService.getStudent(username);
        Review review = reviewRepo.findById(reportRequest.getContentId())
            .orElseThrow(() -> new ReviewNotFoundException(reportRequest.getContentId()));
        
        if (reviewReportRepo.existsByReviewAndStudent(review, student)) {
            throw new ConflictException("You report this review already");
        }

        ReportState reportState = reportStateRepo.findByName(reportStateProperties.getPending());
        ReasonReport reasonReport = reasonReportRepo.findById(reportRequest.getReasonReportId())
            .orElseThrow(() -> new ReasonReportNotFoundException(reportRequest.getReasonReportId()));
        ReviewReport reviewReport = ReviewReport.builder()
            .reasonReport(reasonReport)
            .reportState(reportState)
            .review(review)
            .student(student)
            .datatime(LocalDateTime.now())
            .build();
        reviewReportRepo.save(reviewReport);
    }

    public void reportPost(String username, ReportRequest reportRequest) {
        Student student = userService.getStudent(username);
        Post post = postRepo.findById(reportRequest.getContentId())
            .orElseThrow(() -> new PostNotFoundException(reportRequest.getContentId()));

        if (postReportRepo.existsByPostAndStudent(post, student)) {
            throw new ConflictException("You report this post already");
        }

        ReportState reportState = reportStateRepo.findByName(reportStateProperties.getPending());
        ReasonReport reasonReport = reasonReportRepo.findById(reportRequest.getReasonReportId())
            .orElseThrow(() -> new ReasonReportNotFoundException(reportRequest.getReasonReportId()));
        PostReport postReport = PostReport.builder()
            .reasonReport(reasonReport)
            .reportState(reportState)
            .post(post)
            .student(student)
            .datatime(LocalDateTime.now())
            .build();
        postReportRepo.save(postReport);
    }

    public void reportComment(String username, ReportRequest reportRequest) {

    }

}
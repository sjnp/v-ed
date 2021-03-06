package com.ved.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ved.backend.configuration.ReportStateProperties;
import com.ved.backend.exception.CommentNotFoundException;
import com.ved.backend.exception.PostNotFoundException;
import com.ved.backend.exception.ReasonReportNotFoundException;
import com.ved.backend.exception.ReviewNotFoundException;
import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.model.Comment;
import com.ved.backend.model.CommentReport;
import com.ved.backend.model.Post;
import com.ved.backend.model.PostReport;
import com.ved.backend.model.ReasonReport;
import com.ved.backend.model.ReportState;
import com.ved.backend.model.Review;
import com.ved.backend.model.ReviewReport;
import com.ved.backend.model.Student;
import com.ved.backend.repo.CommentRepo;
import com.ved.backend.repo.CommentReportRepo;
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

    private final CommentRepo commentRepo;
    private final CommentReportRepo commentReportRepo;

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

        if (Objects.isNull(reportRequest.getContentId())) {
            throw new BadRequestException("Content id is required");
        }

        if (Objects.isNull(reportRequest.getReasonReportId())) {
            throw new BadRequestException("Reason report id is required");
        }

        if (Objects.isNull(reportRequest.getReportType())) {
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
        ReasonReport reasonReport = reasonReportRepo.findById(reportRequest.getReasonReportId())
            .orElseThrow(() -> new ReasonReportNotFoundException(reportRequest.getReasonReportId()));

        if (review.getStudent().getAppUser().getUsername().equals(username)) {
            throw new BadRequestException("You can't report review yourself");
        }

        if (reviewReportRepo.existsByReviewAndStudentAndReasonReport(review, student, reasonReport)) {
            throw new ConflictException("You report this review already");
        }

        ReportState reportState = reportStateRepo.findByName(reportStateProperties.getPending());
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
        ReasonReport reasonReport = reasonReportRepo.findById(reportRequest.getReasonReportId())
            .orElseThrow(() -> new ReasonReportNotFoundException(reportRequest.getReasonReportId()));

        if (post.getStudentCourse().getStudent().getAppUser().getUsername().equals(username)) {
            throw new BadRequestException("You can't report post yourself");
        }

        if (postReportRepo.existsByPostAndStudentAndReasonReport(post, student, reasonReport)) {
            throw new ConflictException("You report this post already");
        }

        ReportState reportState = reportStateRepo.findByName(reportStateProperties.getPending());
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
        Student student = userService.getStudent(username);
        Comment comment = commentRepo.findById(reportRequest.getContentId())
            .orElseThrow(() -> new CommentNotFoundException(reportRequest.getContentId()));
        ReasonReport reasonReport = reasonReportRepo.findById(reportRequest.getReasonReportId())
            .orElseThrow(() -> new ReasonReportNotFoundException(reportRequest.getReasonReportId()));

        if (comment.getStudent().getAppUser().getUsername().equals(username)) {
            throw new BadRequestException("You can't report comment yourself");
        }

        if (commentReportRepo.existsByCommentAndStudentAndReasonReport(comment, student, reasonReport)) {
            throw new ConflictException("You report this comment already");
        }

        ReportState reportState = reportStateRepo.findByName(reportStateProperties.getPending());
        CommentReport commentReport = CommentReport.builder()
            .reasonReport(reasonReport)
            .reportState(reportState)
            .comment(comment)
            .student(student)
            .datatime(LocalDateTime.now())
            .build();
        commentReportRepo.save(commentReport);
    }

}
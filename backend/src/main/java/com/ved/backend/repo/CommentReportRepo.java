package com.ved.backend.repo;

import com.ved.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentReportRepo extends JpaRepository<CommentReport, Long> {

    Boolean existsByCommentAndStudentAndReasonReport(Comment comment, Student student, ReasonReport reasonReport);

    List<CommentReport> findAllByReportState(ReportState reportState);
    List<CommentReport> findAllByReportStateAndComment(ReportState reportState, Comment comment);
    Optional<CommentReport> findByIdAndReportState(Long id, ReportState reportState);
}
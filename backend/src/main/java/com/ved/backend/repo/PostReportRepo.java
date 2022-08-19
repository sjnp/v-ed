package com.ved.backend.repo;

import com.ved.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostReportRepo extends JpaRepository<PostReport, Long> {

    Boolean existsByPostAndStudentAndReasonReport(Post post, Student student, ReasonReport reasonReport);

    List<PostReport> findAllByReportState(ReportState reportState);
    List<PostReport> findAllByReportStateAndPost(ReportState reportState, Post post);
    Optional<PostReport> findByIdAndReportState(Long id, ReportState reportState);
}
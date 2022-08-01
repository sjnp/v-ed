package com.ved.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ved.backend.model.Comment;
import com.ved.backend.model.CommentReport;
import com.ved.backend.model.ReasonReport;
import com.ved.backend.model.Student;

public interface CommentReportRepo extends JpaRepository<CommentReport, Long> {

    Boolean existsByCommentAndStudentAndReasonReport(Comment comment, Student student, ReasonReport reasonReport);

}
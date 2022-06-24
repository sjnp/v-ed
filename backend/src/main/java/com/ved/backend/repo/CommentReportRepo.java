package com.ved.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ved.backend.model.CommentReport;

public interface CommentReportRepo extends JpaRepository<CommentReport, Long> {

}
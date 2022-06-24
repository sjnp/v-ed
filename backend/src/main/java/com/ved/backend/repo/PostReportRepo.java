package com.ved.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ved.backend.model.PostReport;

public interface PostReportRepo extends JpaRepository<PostReport, Long> {
 
}
package com.ved.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ved.backend.model.Post;
import com.ved.backend.model.PostReport;
import com.ved.backend.model.Student;

public interface PostReportRepo extends JpaRepository<PostReport, Long> {
 
    Boolean existsByPostAndStudent(Post post, Student student);

}
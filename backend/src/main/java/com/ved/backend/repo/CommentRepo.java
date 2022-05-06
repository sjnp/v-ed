package com.ved.backend.repo;

import com.ved.backend.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    
}
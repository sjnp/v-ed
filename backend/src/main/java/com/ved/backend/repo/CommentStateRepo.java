package com.ved.backend.repo;

import com.ved.backend.model.CommentState;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentStateRepo extends JpaRepository<CommentState, Long> {

    public CommentState findByName(String name);
    
}
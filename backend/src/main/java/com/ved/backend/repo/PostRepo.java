package com.ved.backend.repo;

import com.ved.backend.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {
 
    

}
package com.ved.backend.repo;

import com.ved.backend.model.QuestionBoard;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBoardRepo extends JpaRepository<QuestionBoard, Long> {
 
    

}
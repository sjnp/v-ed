package com.ved.backend.controller;

import com.ved.backend.model.QuestionBoard;
import com.ved.backend.service.QuestionBoardService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question-board")
public class QuestionBoardController {

    private final QuestionBoardService questionBoardService;
   
    public QuestionBoardController(QuestionBoardService questionBoardService) {
        this.questionBoardService = questionBoardService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionBoard questionBoard ) {
        questionBoardService.create(questionBoard);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
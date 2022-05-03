package com.ved.backend.controller;

import java.security.Principal;
import java.util.List;

import com.ved.backend.model.QuestionBoard;
import com.ved.backend.response.QuestionBoardResponse;
import com.ved.backend.service.QuestionBoardService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<QuestionBoardResponse> createQuestion(@RequestBody QuestionBoard questionBoard, Principal principal) {
        QuestionBoardResponse response = questionBoardService.create(questionBoard, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/question/all")
    public ResponseEntity<List<QuestionBoardResponse>> getQuestionAll() {
        List<QuestionBoardResponse> response = questionBoardService.getQuestionAll();
        return ResponseEntity.ok().body(response);
    }

}
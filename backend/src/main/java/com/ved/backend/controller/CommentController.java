package com.ved.backend.controller;

import java.security.Principal;
import java.util.HashMap;

import com.ved.backend.response.CommentResponse;
import com.ved.backend.service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<CommentResponse> createComment(@RequestBody HashMap<String, Object> bodyRequest, Principal principal) {
        
        Long questionBoardId = Long.parseLong(String.valueOf(bodyRequest.get("questionId")));
        String comment = String.valueOf(bodyRequest.get("comment"));
        String username = principal.getName();

        CommentResponse response = commentService.create(questionBoardId, comment, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
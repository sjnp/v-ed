package com.ved.backend.controller;

import java.security.Principal;

import com.ved.backend.model.Answer;
import com.ved.backend.request.AnswerRequest;
import com.ved.backend.service.AssignmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {
 
    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/answer/upload")
    public ResponseEntity<String> createUploadAnswer(@RequestBody AnswerRequest answerRequest, Principal principal) {
        String response = assignmentService.getUploadAnswerURI(answerRequest, principal.getName());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/answer/save")
    public ResponseEntity<Long> saveAnswer(@RequestBody Answer answer, Principal principal) {
        Long answerId = assignmentService.saveAnswer(answer, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(answerId);
    }

    @DeleteMapping("answer/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId, Principal principal) {
        assignmentService.deleteAnswerFile(answerId, principal.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
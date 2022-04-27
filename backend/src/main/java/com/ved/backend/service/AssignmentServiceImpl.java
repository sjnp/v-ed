package com.ved.backend.service;

import java.time.LocalDateTime;

import com.ved.backend.model.Answer;
import com.ved.backend.repo.AnswerRepo;
import com.ved.backend.request.AnswerRequest;

import org.springframework.stereotype.Service;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final PrivateObjectStorageService privateObjectStorageService;
    private final AnswerRepo answerRepo;
 
    public AssignmentServiceImpl(PrivateObjectStorageService privateObjectStorageService, AnswerRepo answerRepo) {
        this.privateObjectStorageService = privateObjectStorageService;
        this.answerRepo = answerRepo;
    }

    public String getUploadAnswerURI(AnswerRequest answerRequest, String username) {
        return privateObjectStorageService.getUploadFileURI(answerRequest, username);
    }

    public Long saveAnswer(Answer answer, String username) {
        answer.setDatetime(LocalDateTime.now());
        
        return answerRepo.save(answer).getId();
    }

    public void deleteAnswerFile(Long answerId, String username) {
        answerRepo.deleteById(answerId);
    }

}
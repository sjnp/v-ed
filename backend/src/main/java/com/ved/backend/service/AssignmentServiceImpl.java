package com.ved.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.Answer;
import com.ved.backend.repo.AnswerRepo;
import com.ved.backend.request.AnswerRequest;

import org.springframework.http.HttpStatus;
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
        
        Optional<Answer> answerOptional = answerRepo.findById(answerId);
        
        if (!answerOptional.isPresent()) {
            throw new MyException("assignment.answer.id.not.found", HttpStatus.NOT_FOUND);
        }

        Answer answer = answerOptional.get();
        answer.setUri("");

        answerRepo.save(answer);
    }

    
}
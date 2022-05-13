package com.ved.backend.service;

import com.ved.backend.model.Answer;
import com.ved.backend.repo.AnswerRepo;
import com.ved.backend.request.AnswerRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Transactional
public class AssignmentService {

    private final PrivateObjectStorageService privateObjectStorageService;
    private final AnswerRepo answerRepo;

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
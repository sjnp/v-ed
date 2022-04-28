package com.ved.backend.service;

import com.ved.backend.model.Answer;
import com.ved.backend.request.AnswerRequest;

public interface AssignmentService {
    
    public String getUploadAnswerURI(AnswerRequest answerRequest, String username);

    public Long saveAnswer(Answer answer, String username);

    public void deleteAnswerFile(Long answerId, String username);

}
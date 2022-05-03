package com.ved.backend.service;

import java.util.List;

import com.ved.backend.model.QuestionBoard;
import com.ved.backend.response.QuestionBoardResponse;

public interface QuestionBoardService {
 
    public QuestionBoardResponse create(QuestionBoard questionBorad, String username);

    public List<QuestionBoardResponse> getQuestionAll();

}
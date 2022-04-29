package com.ved.backend.service;

import java.util.List;

import com.ved.backend.model.QuestionBoard;

public interface QuestionBoardService {
 
    public QuestionBoard create(QuestionBoard questionBorad);

    public List<QuestionBoard> getQuestionAll();

}
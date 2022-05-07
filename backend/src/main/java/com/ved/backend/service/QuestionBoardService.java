package com.ved.backend.service;

import java.util.List;

import com.ved.backend.response.QuestionBoardResponse;

public interface QuestionBoardService {

    public QuestionBoardResponse create(Long courseId, String topic, String detail, String username);

    public QuestionBoardResponse getQuestionBoardById(Long questionBoardId);

    public List<QuestionBoardResponse> getQuestionBoardByCourseId(Long courseId);

}
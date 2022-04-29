package com.ved.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.QuestionBoard;
import com.ved.backend.repo.QuestionBoardRepo;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class QuestionBoardServiceImpl implements QuestionBoardService {

    private final QuestionBoardRepo questionBoardRepo;
 
    public QuestionBoardServiceImpl(QuestionBoardRepo questionBoardRepo) {
        this.questionBoardRepo = questionBoardRepo;
    }

    public QuestionBoard create(QuestionBoard questionBorad) {

        if (questionBorad.getTopic().length() == 0) {
            throw new MyException("question.board.topic.empty", HttpStatus.BAD_REQUEST);
        }

        if (questionBorad.getDetail().length() == 0) {
            throw new MyException("question.board.detail.empty", HttpStatus.BAD_REQUEST);
        }

        questionBorad.setCreatedDateTime(LocalDateTime.now());
        questionBorad.setIsVisible(true);

        return questionBoardRepo.save(questionBorad);
    }

    public List<QuestionBoard> getQuestionAll() {
        
        return questionBoardRepo.findAll();
    }
    
}
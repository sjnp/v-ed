package com.ved.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.QuestionBoard;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.QuestionBoardRepo;
import com.ved.backend.repo.StudentRepo;
import com.ved.backend.response.QuestionBoardResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class QuestionBoardServiceImpl implements QuestionBoardService {

    private final QuestionBoardRepo questionBoardRepo;
    private final AppUserRepo appUserRepo;
    private final StudentRepo studentRepo;
 
    public QuestionBoardServiceImpl(
        QuestionBoardRepo questionBoardRepo, 
        AppUserRepo appUserRepo, 
        StudentRepo studentRepo
    ) {
        this.questionBoardRepo = questionBoardRepo;
        this.appUserRepo = appUserRepo;
        this.studentRepo = studentRepo;
    }

    public QuestionBoardResponse create(QuestionBoard questionBorad, String username) {

        if (questionBorad.getTopic().length() == 0) {
            throw new MyException("question.board.topic.empty", HttpStatus.BAD_REQUEST);
        }

        if (questionBorad.getDetail().length() == 0) {
            throw new MyException("question.board.detail.empty", HttpStatus.BAD_REQUEST);
        }

        Student student = appUserRepo.findByUsername(username).getStudent();

        questionBorad.setVisible(true);
        questionBorad.setCreateDateTime(LocalDateTime.now());
        questionBorad.setStudent(student);

        student.getQuestionBoards().add(questionBorad);

        studentRepo.save(student);
        QuestionBoard questionBoard = questionBoardRepo.save(questionBorad);

        return new QuestionBoardResponse(questionBoard);
    }

    public List<QuestionBoardResponse> getQuestionAll() {
        
        List<QuestionBoard> questionBoards = questionBoardRepo.findAll();

        List<QuestionBoardResponse> questionBoardResponses = new ArrayList<QuestionBoardResponse>();
        for (QuestionBoard questionBoard : questionBoards) {
            questionBoardResponses.add(new QuestionBoardResponse(questionBoard));
        }

        return questionBoardResponses;
    }
    
}
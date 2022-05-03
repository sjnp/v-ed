package com.ved.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.Comment;
import com.ved.backend.model.QuestionBoard;
import com.ved.backend.repo.CommentRepo;
import com.ved.backend.repo.QuestionBoardRepo;
import com.ved.backend.response.CommentResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    private final QuestionBoardRepo questionBoardRepo;
    private final CommentRepo commentRepo;
    
    public CommentServiceImpl(QuestionBoardRepo questionBoardRepo, CommentRepo commentRepo) {
        this.questionBoardRepo = questionBoardRepo;
        this.commentRepo = commentRepo;
    }

    public CommentResponse create(Long questionBoardId, String comment) {

        Optional<QuestionBoard> questionBoardOptional = questionBoardRepo.findById(questionBoardId);

        if (questionBoardOptional.isEmpty()) {
            throw new MyException("question.board.id.not.found", HttpStatus.BAD_REQUEST);
        }

        QuestionBoard questionBoard = questionBoardOptional.get();

        Comment commentEntity = new Comment();
        commentEntity.setComment(comment);
        commentEntity.setCommentDateTime(LocalDateTime.now());
        commentEntity.setQuestionBoard(questionBoard);
        commentEntity.setVisible(true);

        questionBoard.getComments().add(commentEntity);

        commentRepo.save(commentEntity);
        questionBoardRepo.save(questionBoard);
        
        return new CommentResponse(commentEntity);
    }
    
}
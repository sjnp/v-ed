package com.ved.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Comment;
import com.ved.backend.model.CommentState;
import com.ved.backend.model.QuestionBoard;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CommentRepo;
import com.ved.backend.repo.CommentStateRepo;
import com.ved.backend.repo.QuestionBoardRepo;
import com.ved.backend.repo.StudentRepo;
import com.ved.backend.response.CommentResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    private final QuestionBoardRepo questionBoardRepo;
    private final CommentRepo commentRepo;
    private final AppUserRepo appUserRepo;
    private final CommentStateRepo commentStateRepo;
    private final StudentRepo studentRepo;
    
    public CommentServiceImpl(
        QuestionBoardRepo questionBoardRepo, 
        CommentRepo commentRepo, 
        AppUserRepo appUserRepo,
        CommentStateRepo commentStateRepo,
        StudentRepo studentRepo
    ) {
        this.questionBoardRepo = questionBoardRepo;
        this.commentRepo = commentRepo;
        this.appUserRepo = appUserRepo;
        this.commentStateRepo = commentStateRepo;
        this.studentRepo = studentRepo;
    }

    public CommentResponse create(Long questionBoardId, String comment, String username) {

        Optional<QuestionBoard> questionBoardOptional = questionBoardRepo.findById(questionBoardId);

        if (questionBoardOptional.isEmpty()) {
            throw new MyException("question.board.id.not.found", HttpStatus.BAD_REQUEST);
        }

        Student student = appUserRepo.findByUsername(username).getStudent();

        CommentState commentState = commentStateRepo.findByName("OWNER");


        QuestionBoard questionBoard = questionBoardOptional.get();

        Comment commentEntity = new Comment();
        commentEntity.setComment(comment);
        commentEntity.setCommentDateTime(LocalDateTime.now());
        commentEntity.setQuestionBoard(questionBoard);
        commentEntity.setVisible(true);
        commentEntity.setStudent(student);
        commentEntity.setCommentState(commentState);
        
        student.getComments().add(commentEntity);

        questionBoard.getComments().add(commentEntity);

        commentRepo.save(commentEntity);
        questionBoardRepo.save(questionBoard);
        studentRepo.save(student);

        return new CommentResponse(commentEntity);
    }
    
}
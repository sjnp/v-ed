package com.ved.backend.service;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.Comment;
import com.ved.backend.model.CommentState;
import com.ved.backend.model.Post;
import com.ved.backend.model.Student;
import com.ved.backend.repo.*;
import com.ved.backend.response.CommentResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class CommentService {

    private final PostRepo postRepo;
    private final CommentRepo commentRepo;
    private final AppUserRepo appUserRepo;
    private final CommentStateRepo commentStateRepo;
    private final StudentRepo studentRepo;

    public CommentResponse create(Long questionBoardId, String comment, String username) {

        Optional<Post> questionBoardOptional = postRepo.findById(questionBoardId);

        if (questionBoardOptional.isEmpty()) {
            throw new MyException("question.board.id.not.found", HttpStatus.BAD_REQUEST);
        }

        Student student = appUserRepo.findByUsername(username).getStudent();

        CommentState commentState = commentStateRepo.findByName("OWNER");


        Post post = questionBoardOptional.get();

        Comment commentEntity = new Comment();
        commentEntity.setComment(comment);
        commentEntity.setCommentDateTime(LocalDateTime.now());
        commentEntity.setPost(post);
        commentEntity.setVisible(true);
        commentEntity.setStudent(student);
        commentEntity.setCommentState(commentState);

        student.getComments().add(commentEntity);

        post.getComments().add(commentEntity);

        commentRepo.save(commentEntity);
        postRepo.save(post);
        studentRepo.save(student);

        return new CommentResponse(commentEntity);
    }

}
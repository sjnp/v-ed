package com.ved.backend.response;

import java.time.LocalDateTime;

import com.ved.backend.model.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentResponse {
 
    private Long id;
    
    private String comment;
    
    private LocalDateTime commentDateTime;
    
    private boolean visible;
    
    private String commentState;

    private String profilePictureUrl;
    
    private String firstname;
    
    private String lastname;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.commentDateTime = comment.getCommentDateTime();
        this.visible = comment.isVisible();
        this.commentState = comment.getCommentState().getName();
        this.profilePictureUrl = comment.getStudent().getProfilePicUri();
        this.firstname = comment.getStudent().getFirstName();
        this.lastname = comment.getStudent().getLastName();
    }

}
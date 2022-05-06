package com.ved.backend.response;

import java.time.LocalDateTime;

import com.ved.backend.model.Comment;

public class CommentResponse {
 
    private Long id;
    private String comment;
    private LocalDateTime commentDateTime;
    private boolean visible;
    private String commentState;
    private String firstname;
    private String lastname;

    public CommentResponse() {
    }

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.commentDateTime = comment.getCommentDateTime();
        this.visible = comment.isVisible();
        this.commentState = comment.getCommentState().getName();
        this.firstname = comment.getStudent().getFirstName();
        this.lastname = comment.getStudent().getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCommentDateTime() {
        return commentDateTime;
    }

    public void setCommentDateTime(LocalDateTime commentDateTime) {
        this.commentDateTime = commentDateTime;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getCommentState() {
        return commentState;
    }

    public void setCommentState(String commentState) {
        this.commentState = commentState;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
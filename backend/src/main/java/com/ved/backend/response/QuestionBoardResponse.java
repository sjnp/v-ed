package com.ved.backend.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ved.backend.model.Comment;
import com.ved.backend.model.QuestionBoard;

public class QuestionBoardResponse {
 
    private Long id;
    private String topic;
    private String detail;
    private LocalDateTime createDateTime;
    private boolean visible;
    private List<CommentResponse> comments;
    private String firstname;
    private String lastname;
    
    public QuestionBoardResponse() {
    }

    public QuestionBoardResponse(QuestionBoard questionBoard) {
        this.id = questionBoard.getId();
        this.topic = questionBoard.getTopic();
        this.detail = questionBoard.getDetail();
        this.createDateTime = questionBoard.getCreateDateTime();
        this.visible = questionBoard.isVisible();
        this.comments = this.getListComemnt(questionBoard.getComments());
        this.firstname = questionBoard.getStudent().getFirstName();
        this.lastname = questionBoard.getStudent().getLastName();
    }

    private List<CommentResponse> getListComemnt(List<Comment> comments) {
        List<CommentResponse> commentResponses = new ArrayList<CommentResponse>();
        for (Comment comment : comments) {
            commentResponses.add(new CommentResponse(comment));
        }
        return commentResponses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
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
package com.ved.backend.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime commentDateTime;

    @Column(nullable = false)
    private boolean visible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "question_board_comment",
        joinColumns = { @JoinColumn(name = "comment_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "question_board_id", referencedColumnName = "id") })
    private QuestionBoard questionBoard;

    public Comment() {
    }

    public Comment(
        Long id, 
        String comment, 
        LocalDateTime commentDateTime, 
        boolean visible,
        QuestionBoard questionBoard
    ) {
        this.id = id;
        this.comment = comment;
        this.commentDateTime = commentDateTime;
        this.visible = visible;
        this.questionBoard = questionBoard;
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

    public QuestionBoard getQuestionBoard() {
        return questionBoard;
    }

    public void setQuestionBoard(QuestionBoard questionBoard) {
        this.questionBoard = questionBoard;
    }

}
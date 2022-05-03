package com.ved.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class QuestionBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private LocalDateTime createDateTime;

    @Column(nullable = false)
    private boolean visible;

    @OneToMany(mappedBy = "questionBoard", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public QuestionBoard() {
    }

    public QuestionBoard(
        Long id, 
        String topic, 
        String detail, 
        LocalDateTime createDateTime, 
        boolean visible,
        List<Comment> comments
    ) {
        this.id = id;
        this.topic = topic;
        this.detail = detail;
        this.createDateTime = createDateTime;
        this.visible = visible;
        this.comments = comments;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
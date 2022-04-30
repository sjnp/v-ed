package com.ved.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;

import javax.persistence.Column;

@Entity
@Table
public class QuestionBoard {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false, length = 200)
    private String topic;

    @Column(nullable = false, length = 1000)
    private String detail;

    @Column(nullable = false)
    private LocalDateTime createDateTime;

    @Column(nullable = false)
    private Boolean visible;

    public QuestionBoard() {
    }

    public QuestionBoard(Long id, String topic, String detail, LocalDateTime createDateTime, Boolean visible) {
        this.id = id;
        this.topic = topic;
        this.detail = detail;
        this.createDateTime = createDateTime;
        this.visible = visible;
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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

}
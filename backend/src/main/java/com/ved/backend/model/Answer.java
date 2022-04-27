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
public class Answer {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(nullable = false)
    private Long chapter;

    @Column(nullable = false)
    private Long no;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = true)
    private String commentInstructor;

    public Answer() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChapter() {
        return chapter;
    }

    public void setChapter(Long chapter) {
        this.chapter = chapter;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCommentInstructor() {
        return commentInstructor;
    }

    public void setCommentInstructor(String commentInstructor) {
        this.commentInstructor = commentInstructor;
    }

}
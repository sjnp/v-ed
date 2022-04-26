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
    private Long section;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Column(nullable = false)
    private String uri;

    @Column(nullable = true)
    private String commentInstructor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSection() {
        return section;
    }

    public void setSection(Long section) {
        this.section = section;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCommentInstructor() {
        return commentInstructor;
    }

    public void setCommentInstructor(String commentInstructor) {
        this.commentInstructor = commentInstructor;
    }    
    
}
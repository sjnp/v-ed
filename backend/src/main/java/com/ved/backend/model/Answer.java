package com.ved.backend.model;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;

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

  @ManyToOne(fetch = LAZY)
  @JoinTable(name = "student_course_answer",
      joinColumns = {@JoinColumn(name = "answer_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "student_course_id", referencedColumnName = "id")})
  private StudentCourse studentCourse;

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

  public StudentCourse getStudentCourse() {
    return studentCourse;
  }

  public void setStudentCourse(StudentCourse studentCourse) {
    this.studentCourse = studentCourse;
  }

  public Answer() {
  }

  public Answer(Long id, Long chapter, Long no, LocalDateTime datetime, String fileName, String commentInstructor, StudentCourse studentCourse) {
    this.id = id;
    this.chapter = chapter;
    this.no = no;
    this.datetime = datetime;
    this.fileName = fileName;
    this.commentInstructor = commentInstructor;
    this.studentCourse = studentCourse;
  }
}
package com.ved.backend.model;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "student_course")
public class StudentCourse {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String chargeId;

  private String transferId;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @OneToMany(mappedBy = "studentCourse", cascade = CascadeType.ALL)
  private List<Answer> answers;

  @OneToMany(mappedBy = "studentCourse", cascade = CascadeType.ALL)
  private List<Post> posts;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getChargeId() {
    return chargeId;
  }

  public void setChargeId(String chargeId) {
    this.chargeId = chargeId;
  }

  public String getTransferId() {
    return transferId;
  }

  public void setTransferId(String transferId) {
    this.transferId = transferId;
  }

  public List<Answer> getAnswers() {
    return answers;
  }

  public void setAnswers(List<Answer> answers) {
    this.answers = answers;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public List<Post> getQuestionBoards() {
    return posts;
  }

  public void setQuestionBoards(List<Post> posts) {
    this.posts = posts;
  }

  public StudentCourse() {
  }

  public StudentCourse(Long id, String chargeId, String transferId, Student student, Course course, List<Answer> answers, List<Post> posts) {
    this.id = id;
    this.chargeId = chargeId;
    this.transferId = transferId;
    this.student = student;
    this.course = course;
    this.answers = answers;
    this.posts = posts;
  }
}

package com.ved.backend.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table
public class Instructor {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Column(nullable = false)
  private String recipientId;

  @OneToOne(mappedBy = "instructor")
  private Student student;

  @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
  private List<Course> courses = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRecipientId() {
    return recipientId;
  }

  public void setRecipientId(String recipientId) {
    this.recipientId = recipientId;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  public Instructor() {
  }

  public Instructor(Long id, String recipientId, Student student, List<Course> courses) {
    this.id = id;
    this.recipientId = recipientId;
    this.student = student;
    this.courses = courses;
  }

}

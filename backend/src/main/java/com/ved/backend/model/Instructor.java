package com.ved.backend.model;

import javax.persistence.*;

import java.util.Set;

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

  @OneToMany(mappedBy = "instructor")
  private Set<Course> courses;

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

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }

  public Instructor() {
  }

  public Instructor(Long id, String recipientId, Student student, Set<Course> courses) {
    this.id = id;
    this.recipientId = recipientId;
    this.student = student;
    this.courses = courses;
  }

}

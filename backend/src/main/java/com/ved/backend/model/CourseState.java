package com.ved.backend.model;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table
public class CourseState {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @OneToMany(mappedBy = "courseState")
  private Set<Course> courses;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }

  public CourseState() {
  }

  public CourseState(Long id, String name, Set<Course> courses) {
    this.id = id;
    this.name = name;
    this.courses = courses;
  }
}

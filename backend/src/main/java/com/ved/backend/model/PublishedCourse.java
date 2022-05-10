package com.ved.backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class PublishedCourse {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Double totalScore;

  private Long totalUser;

  private Double star;

  @OneToOne(mappedBy = "publishedCourse")
  private Course course;

  @OneToMany(mappedBy = "publishedCourse", cascade = CascadeType.ALL)
  private List<Review> reviews;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(Double totalScore) {
    this.totalScore = totalScore;
  }

  public Long getTotalUser() {
    return totalUser;
  }

  public void setTotalUser(Long totalUser) {
    this.totalUser = totalUser;
  }

  public Double getStar() {
    return star;
  }

  public void setStar(Double star) {
    this.star = star;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public PublishedCourse() {
  }

  public PublishedCourse(Long id, Double totalScore, Long totalUser, Double star, Course course, List<Review> reviews) {
    this.id = id;
    this.totalScore = totalScore;
    this.totalUser = totalUser;
    this.star = star;
    this.course = course;
    this.reviews = reviews;
  }
}

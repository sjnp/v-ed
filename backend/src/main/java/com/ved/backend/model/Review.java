package com.ved.backend.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

@Entity
@Table
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private double rating;

  @Column(nullable = false, length = 1000)
  private String comment;

  @Column(nullable = false)
  private LocalDateTime reviewDateTime;

  @Column(nullable = false)
  private boolean visible;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "published_course_review",
      joinColumns = {@JoinColumn(name = "review_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "published_course_id", referencedColumnName = "id")})
  private PublishedCourse publishedCourse;

  @OneToMany(mappedBy = "review")
  private List<ReviewReport> reviewReports;

  public Review() {
  }

  public Review(Long id, double rating, String comment, LocalDateTime reviewDateTime, boolean visible, PublishedCourse publishedCourse, List<ReviewReport> reviewReports) {
    this.id = id;
    this.rating = rating;
    this.comment = comment;
    this.reviewDateTime = reviewDateTime;
    this.visible = visible;
    this.publishedCourse = publishedCourse;
    this.reviewReports = reviewReports;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public LocalDateTime getReviewDateTime() {
    return reviewDateTime;
  }

  public void setReviewDateTime(LocalDateTime reviewDateTime) {
    this.reviewDateTime = reviewDateTime;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public PublishedCourse getPublishedCourse() {
    return publishedCourse;
  }

  public void setPublishedCourse(PublishedCourse publishedCourse) {
    this.publishedCourse = publishedCourse;
  }

  public List<ReviewReport> getReviewReports() {
    return reviewReports;
  }

  public void setReviewReports(List<ReviewReport> reviewReports) {
    this.reviewReports = reviewReports;
  }
}
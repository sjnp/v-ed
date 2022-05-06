package com.ved.backend.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table
public class ReviewReport {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "review_review_report",
      joinColumns = {@JoinColumn(name = "review_report_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "review_id", referencedColumnName = "id")})
  private Review review;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "review_report_report_state",
      joinColumns = {@JoinColumn(name = "review_report_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "report_state_id", referencedColumnName = "id")})
  private ReportState reportState;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Review getReview() {
    return review;
  }

  public void setReview(Review review) {
    this.review = review;
  }

  public ReportState getReportState() {
    return reportState;
  }

  public void setReportState(ReportState reportState) {
    this.reportState = reportState;
  }

  public ReviewReport() {
  }

  public ReviewReport(Long id, String description, Review review, ReportState reportState) {
    this.id = id;
    this.description = description;
    this.review = review;
    this.reportState = reportState;
  }
}

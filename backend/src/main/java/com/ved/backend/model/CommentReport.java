package com.ved.backend.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table
public class CommentReport {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "comment_comment_report",
      joinColumns = {@JoinColumn(name = "comment_report_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "comment_id", referencedColumnName = "id")})
  private Comment comment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "comment_report_report_state",
      joinColumns = {@JoinColumn(name = "comment_report_id", referencedColumnName = "id")},
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

  public Comment getComment() {
    return comment;
  }

  public void setComment(Comment comment) {
    this.comment = comment;
  }

  public ReportState getReportState() {
    return reportState;
  }

  public void setReportState(ReportState reportState) {
    this.reportState = reportState;
  }

  public CommentReport() {
  }

  public CommentReport(Long id, String description, Comment comment, ReportState reportState) {
    this.id = id;
    this.description = description;
    this.comment = comment;
    this.reportState = reportState;
  }
}

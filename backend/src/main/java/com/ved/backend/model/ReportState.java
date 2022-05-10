package com.ved.backend.model;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table
public class ReportState {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @OneToMany(mappedBy = "reportState")
  private List<ReviewReport> reviewReports;

  @OneToMany(mappedBy = "reportState")
  private List<CommentReport> commentReports;

  @OneToMany(mappedBy = "reportState")
  private List<QuestionReport> questionReports;

  public ReportState() {
  }

  public ReportState(Long id, String name, List<ReviewReport> reviewReports, List<CommentReport> commentReports, List<QuestionReport> questionReports) {
    this.id = id;
    this.name = name;
    this.reviewReports = reviewReports;
    this.commentReports = commentReports;
    this.questionReports = questionReports;
  }

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

  public List<ReviewReport> getReviewReports() {
    return reviewReports;
  }

  public void setReviewReports(List<ReviewReport> reviewReports) {
    this.reviewReports = reviewReports;
  }

  public List<CommentReport> getCommentReports() {
    return commentReports;
  }

  public void setCommentReports(List<CommentReport> commentReports) {
    this.commentReports = commentReports;
  }

  public List<QuestionReport> getQuestionReports() {
    return questionReports;
  }

  public void setQuestionReports(List<QuestionReport> questionReports) {
    this.questionReports = questionReports;
  }
}

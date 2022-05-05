package com.ved.backend.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table
public class QuestionReport {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "question_board_question_report",
      joinColumns = {@JoinColumn(name = "question_report_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "question_board_id", referencedColumnName = "id")})
  private QuestionBoard questionBoard;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "question_report_report_state",
      joinColumns = {@JoinColumn(name = "question_report_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "report_state_id", referencedColumnName = "id")})
  private ReportState reportState;

  public QuestionReport() {
  }

  public QuestionReport(Long id, String description, QuestionBoard questionBoard, ReportState reportState) {
    this.id = id;
    this.description = description;
    this.questionBoard = questionBoard;
    this.reportState = reportState;
  }

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

  public QuestionBoard getQuestionBoard() {
    return questionBoard;
  }

  public void setQuestionBoard(QuestionBoard questionBoard) {
    this.questionBoard = questionBoard;
  }

  public ReportState getReportState() {
    return reportState;
  }

  public void setReportState(ReportState reportState) {
    this.reportState = reportState;
  }
}

package com.ved.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

  @ManyToOne
  @JoinColumn(name = "reason_report_id")
  private ReasonReport reasonReport;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

}
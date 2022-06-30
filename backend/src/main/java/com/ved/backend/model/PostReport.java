package com.ved.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class PostReport {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime datatime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "post_post_report",
      joinColumns = {@JoinColumn(name = "post_report_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "id")})
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "post_report_report_state",
      joinColumns = {@JoinColumn(name = "post_report_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "report_state_id", referencedColumnName = "id")})
  private ReportState reportState;

  @ManyToOne
  @JoinColumn(name = "reason_report_id")
  private ReasonReport reasonReport;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

}
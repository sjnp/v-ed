package com.ved.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class PostReport {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String description;

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

}

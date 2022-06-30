package com.ved.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
  private List<PostReport> postReports;

}
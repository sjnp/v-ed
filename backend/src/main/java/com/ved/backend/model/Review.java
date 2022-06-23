package com.ved.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private Double rating;

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

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "review_student",
      joinColumns = { @JoinColumn(name = "review_id", referencedColumnName = "id") },
      inverseJoinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "id")})
  private Student student;

}
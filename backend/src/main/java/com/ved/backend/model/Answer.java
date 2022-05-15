package com.ved.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Answer {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Column(nullable = false)
  private Integer chapterIndex;

  @Column(nullable = false)
  private Integer sectionIndex;

  @Column(nullable = false)
  private LocalDateTime datetime;

  @Column(nullable = false)
  private String fileName;

  @Column(nullable = true)
  private String commentInstructor;

  @ManyToOne(fetch = LAZY)
  @JoinTable(name = "student_course_answer",
      joinColumns = {@JoinColumn(name = "answer_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "student_course_id", referencedColumnName = "id")})
  private StudentCourse studentCourse;

}
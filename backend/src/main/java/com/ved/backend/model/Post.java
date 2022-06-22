package com.ved.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, length = 200)
  private String topic;

  @Column(nullable = false, length = 1000)
  private String detail;

  @Column(nullable = false)
  private LocalDateTime createDateTime;

  @Column(nullable = false)
  private boolean visible;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "student_course_post",
      joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "student_course_id", referencedColumnName = "id")})
  private StudentCourse studentCourse;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<Comment> comments = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "course_post",
      joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")})
  private Course course;

  @OneToMany(mappedBy = "post")
  private List<PostReport> postReports;

}
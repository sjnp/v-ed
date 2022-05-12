package com.ved.backend.model;

import lombok.AllArgsConstructor;
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
@Entity
@Table(name = "student_course")
public class StudentCourse {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String chargeId;

  private String transferId;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @OneToMany(mappedBy = "studentCourse", cascade = CascadeType.ALL)
  private List<Answer> answers;

  @OneToMany(mappedBy = "studentCourse", cascade = CascadeType.ALL)
  private List<Post> posts;

}

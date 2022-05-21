package com.ved.backend.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Student {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  private String occupation;

  @Column(length = 1024)
  private String biography;

  private String profilePicUri;

  @OneToOne(mappedBy = "student")
  private AppUser appUser;

  @OneToMany(mappedBy = "student")
  private List<Comment> comments;

  @OneToMany(mappedBy = "student")
  private List<StudentCourse> studentCourses;

  @OneToOne(mappedBy = "student")
  private Review review;

  @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
  @JoinTable(name = "student_instructor",
      joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "instructor_id", referencedColumnName = "id")})
  private Instructor instructor;

  public String getFullName() { return firstName + " " + lastName; }

}

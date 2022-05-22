package com.ved.backend.model;

import com.ved.backend.utility.ListConverter;
import lombok.*;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Course {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Long price;

  @Column(length = 1024, nullable = false)
  private String overview;

  @Column(length = 1024, nullable = false)
  private String requirement;

  private String pictureUrl;

  @Convert(converter = ListConverter.class)
  @Column(columnDefinition = "jsonb", nullable = false)
  private List<Chapter> chapters;

  @ManyToOne(fetch = EAGER)
  @JoinTable(name = "course_category",
      joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")})
  private Category category;

  @ManyToOne(fetch = EAGER)
  @JoinTable(name = "course_course_state",
      joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "course_state_id", referencedColumnName = "id")})
  private CourseState courseState;

  @ManyToOne(fetch = LAZY)
  @JoinTable(name = "instructor_course",
      joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "instructor_id", referencedColumnName = "id")})
  private Instructor instructor;

  @OneToMany(mappedBy = "course")
  private List<StudentCourse> studentCourses;

  @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
  @JoinTable(name = "course_published_course",
      joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "published_course_id", referencedColumnName = "id")})
  private PublishedCourse publishedCourse;

  @OneToMany(mappedBy = "course")
  private List<Post> posts;

}

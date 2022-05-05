package com.ved.backend.model;

import com.ved.backend.utility.ListConverter;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

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
  @Column(columnDefinition = "jsonb")
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
  private List<QuestionBoard> questionBoards;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getRequirement() {
    return requirement;
  }

  public void setRequirement(String requirement) {
    this.requirement = requirement;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
  }

  public List<Chapter> getChapters() {
    return chapters;
  }

  public void setChapters(List<Chapter> chapters) {
    this.chapters = chapters;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public CourseState getCourseState() {
    return courseState;
  }

  public void setCourseState(CourseState courseState) {
    this.courseState = courseState;
  }

  public Instructor getInstructor() {
    return instructor;
  }

  public void setInstructor(Instructor instructor) {
    this.instructor = instructor;
  }

  public List<StudentCourse> getStudentCourses() {
    return studentCourses;
  }

  public void setStudentCourses(List<StudentCourse> studentCourses) {
    this.studentCourses = studentCourses;
  }

  public PublishedCourse getPublishedCourse() {
    return publishedCourse;
  }

  public void setPublishedCourse(PublishedCourse publishedCourse) {
    this.publishedCourse = publishedCourse;
  }

  public List<QuestionBoard> getQuestionBoards() {
    return questionBoards;
  }

  public void setQuestionBoards(List<QuestionBoard> questionBoards) {
    this.questionBoards = questionBoards;
  }

  public Course() {
  }

  public Course(Long id, String name, Long price, String overview, String requirement, String pictureUrl, List<Chapter> chapters, Category category, CourseState courseState, Instructor instructor, List<StudentCourse> studentCourses, PublishedCourse publishedCourse, List<QuestionBoard> questionBoards) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.overview = overview;
    this.requirement = requirement;
    this.pictureUrl = pictureUrl;
    this.chapters = chapters;
    this.category = category;
    this.courseState = courseState;
    this.instructor = instructor;
    this.studentCourses = studentCourses;
    this.publishedCourse = publishedCourse;
    this.questionBoards = questionBoards;
  }
}

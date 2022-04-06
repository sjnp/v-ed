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

  @Convert(converter = ListConverter.class)
  @Column(columnDefinition = "jsonb")
  private List<Chapter> chapters;

  @ManyToOne(fetch = EAGER)
  @JoinTable(name = "course_category",
      joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")})
  private Category category;

  @ManyToOne(fetch = LAZY)
  @JoinTable(name = "instructor_course",
      joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "instructor_id", referencedColumnName = "id")})
  private Instructor instructor;

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

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Instructor getInstructor() {
    return instructor;
  }

  public void setInstructor(Instructor instructor) {
    this.instructor = instructor;
  }

  public Course() {
  }

  public Course(Long id, String name, Long price, String overview, String requirement, Category category, Instructor instructor) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.overview = overview;
    this.requirement = requirement;
    this.category = category;
    this.instructor = instructor;
  }

}

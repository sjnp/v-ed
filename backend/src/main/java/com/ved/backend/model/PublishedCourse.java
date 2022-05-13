package com.ved.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class PublishedCourse {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Double totalScore;

  private Long totalUser;

  private Double star;

  @OneToOne(mappedBy = "publishedCourse")
  private Course course;

  @OneToMany(mappedBy = "publishedCourse", cascade = CascadeType.ALL)
  private List<Review> reviews;

}

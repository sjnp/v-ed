package com.ved.backend.model;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Instructor {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Column(nullable = false)
  private String recipientId;

  @OneToOne(mappedBy = "instructor")
  private Student student;

  @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
  private List<Course> courses = new ArrayList<>();

}

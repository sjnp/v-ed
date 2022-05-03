package com.ved.backend.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

import java.util.List;

import static javax.persistence.FetchType.LAZY;

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
  private List<QuestionBoard> questionBoards;

  @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
  @JoinTable(name = "student_instructor",
      joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "instructor_id", referencedColumnName = "id")})
  private Instructor instructor;

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getOccupation() {
    return occupation;
  }

  public String getBiography() {
    return biography;
  }

  public String getProfilePicUri() {
    return profilePicUri;
  }

  public AppUser getAppUser() {
    return appUser;
  }

  public Instructor getInstructor() {
    return instructor;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }

  public void setProfilePicUri(String profilePicUri) {
    this.profilePicUri = profilePicUri;
  }

  public void setAppUser(AppUser appUser) {
    this.appUser = appUser;
  }

  public void setInstructor(Instructor instructor) {
    this.instructor = instructor;
  }

  public List<QuestionBoard> getQuestionBoards() {
    return questionBoards;
  }

  public void setQuestionBoards(List<QuestionBoard> questionBoards) {
    this.questionBoards = questionBoards;
  }

  public Student() {
  }

  public Student(
    Long id, 
    String firstName, 
    String lastName, 
    String occupation, 
    String biography, 
    String profilePicUri, 
    AppUser appUser, 
    Instructor instructor,
    List<QuestionBoard> questionBoards
  ) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.occupation = occupation;
    this.biography = biography;
    this.profilePicUri = profilePicUri;
    this.appUser = appUser;
    this.instructor = instructor;
    this.questionBoards = questionBoards;
  }

}

package com.ved.backend.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table
public class PersonalInfo {
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

  @OneToOne(mappedBy = "personalInfo")
  private AppUser appUser;

  public AppUser getAppUser() {
    return appUser;
  }

  public void setAppUser(AppUser appUser) {
    this.appUser = appUser;
  }

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

  public PersonalInfo() {
  }

  public PersonalInfo(Long id, String firstName, String lastName, String occupation, String biography, AppUser appUser) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.occupation = occupation;
    this.biography = biography;
    this.appUser = appUser;
  }

}

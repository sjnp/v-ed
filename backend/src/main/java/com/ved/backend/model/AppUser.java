package com.ved.backend.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table
public class AppUser {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @ManyToMany(fetch = EAGER)
  private Collection<AppRole> appRoles = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
  @JoinTable(name = "app_user_personal_info",
      joinColumns = { @JoinColumn(name = "app_user_id", referencedColumnName = "id") },
      inverseJoinColumns = { @JoinColumn(name = "personal_info_id", referencedColumnName = "id")})
  private PersonalInfo personalInfo;

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Collection<AppRole> getAppRoles() {
    return appRoles;
  }

  public PersonalInfo getPersonalInfo() {
    return personalInfo;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username.toLowerCase();
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setAppRoles(Collection<AppRole> appRoles) {
    this.appRoles = appRoles;
  }

  public void setPersonalInfo(PersonalInfo personalInfo) {
    this.personalInfo = personalInfo;
  }

  public AppUser() {
  }

  public AppUser(Long id, String username, String password, Collection<AppRole> appRoles, PersonalInfo personalInfo) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.appRoles = appRoles;
    this.personalInfo = personalInfo;
  }
}

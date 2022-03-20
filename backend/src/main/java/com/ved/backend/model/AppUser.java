package com.ved.backend.model;

import javax.persistence.*;

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

  @ManyToOne
  @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false)
  private AppRole appRole;

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public AppRole getAppRole() {
    return appRole;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setAppRole(AppRole appRole) {
    this.appRole = appRole;
  }

  public AppUser() {
  }

  public AppUser(Long id, String username, String password, AppRole appRole) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.appRole = appRole;
  }
}

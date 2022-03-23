package com.ved.backend.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static javax.persistence.FetchType.EAGER;
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

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username.toLowerCase();
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setAppRole(Collection<AppRole> appRoles) {
    this.appRoles = appRoles;
  }

  public AppUser() {
  }

  public AppUser(Long id, String username, String password, Collection<AppRole> appRoles) {
    this.id = id;
    this.username = username.toLowerCase();
    this.password = password;
    this.appRoles = appRoles;
  }
}

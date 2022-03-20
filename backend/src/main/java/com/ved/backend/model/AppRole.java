package com.ved.backend.model;

import javax.persistence.*;


import static javax.persistence.GenerationType.AUTO;

@Entity
@Table
public class AppRole {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AppRole() {
  }

  public AppRole(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}

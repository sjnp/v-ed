package com.ved.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
  @JoinTable(name = "app_user_student",
      joinColumns = { @JoinColumn(name = "app_user_id", referencedColumnName = "id") },
      inverseJoinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "id")})
  private Student student;

}

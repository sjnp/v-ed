package com.ved.backend.repo;

import com.ved.backend.model.AppUser;
import com.ved.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {
  AppUser findByUsername(String username);
}

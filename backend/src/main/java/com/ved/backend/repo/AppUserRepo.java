package com.ved.backend.repo;

import com.ved.backend.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {
  AppUser findByUsername(String username);
}

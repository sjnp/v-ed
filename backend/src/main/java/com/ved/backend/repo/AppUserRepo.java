package com.ved.backend.repo;

import com.ved.backend.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

  AppUser findByUsername(String username);
  Optional<AppUser> findAppUserByUsername(String username);
  boolean existsByUsername(String username);

}
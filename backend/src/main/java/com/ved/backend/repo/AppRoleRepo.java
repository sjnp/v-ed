package com.ved.backend.repo;

import com.ved.backend.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepo extends JpaRepository<AppRole, Long> {
  AppRole findByName(String name);
}

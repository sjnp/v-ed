package com.ved.backend.repo;

import com.ved.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CategoryRepo extends JpaRepository<Category, Long> {

  interface IdAndNameOnly {
    String getId();
    String getName();
  }

  Category findByName(String name);
  Collection<IdAndNameOnly> findAllBy();
}

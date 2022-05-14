package com.ved.backend.repo;

import com.ved.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {

  interface IdAndNameOnly {
    String getId();
    String getName();
  }

  Collection<IdAndNameOnly> findAllBy();

  Optional<Category> findByName(String name);

}

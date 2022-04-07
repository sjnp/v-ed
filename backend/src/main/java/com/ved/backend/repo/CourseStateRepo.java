package com.ved.backend.repo;

import com.ved.backend.model.CourseState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseStateRepo extends JpaRepository<CourseState, Long> {
  CourseState findByName(String name);
}

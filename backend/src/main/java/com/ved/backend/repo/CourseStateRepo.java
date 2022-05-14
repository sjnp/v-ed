package com.ved.backend.repo;

import com.ved.backend.model.CourseState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;


public interface CourseStateRepo extends JpaRepository<CourseState, Long> {

  interface IdAndNameOnly {
    String getId();
    String getName();
  }

  CourseState findByName(String name);
  Collection<IdAndNameOnly> findAllBy();

  Optional<CourseState> findCourseStateByName(String name);
}

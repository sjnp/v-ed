package com.ved.backend.repo;

import com.ved.backend.model.Chapter;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Long> {

  interface CourseMaterials {
    Long getId();
    String getName();
    Long getPrice();
    String getPictureUrl();
    List<Chapter> getChapters();
  }

  CourseMaterials findCourseByInstructorAndCourseStateAndId(Instructor instructor, CourseState courseState, Long id);
}

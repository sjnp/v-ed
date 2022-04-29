package com.ved.backend.repo;

import com.ved.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course, Long> {

  interface CourseMaterials {
    Long getId();
    String getName();
    Long getPrice();
    String getPictureUrl();
    List<Chapter> getChapters();
  }

  CourseMaterials findCourseByInstructorAndCourseStateAndId(Instructor instructor, CourseState courseState, Long id);
  List<Course> findCoursesByCourseState(CourseState courseState);
  Course findCourseByCourseStateAndId(CourseState courseState,Long id);
}

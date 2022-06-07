package com.ved.backend.repo;

import com.ved.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Long> {

  Optional<Course> findByInstructorAndCourseStateAndId(Instructor instructor, CourseState courseState, Long id);

  List<Course> findCoursesByCourseState(CourseState courseState);

  List<Course> findCoursesByCourseStateAndInstructor(CourseState courseState, Instructor instructor);

  // move new refactor optional below
  List<Course> findCoursesByCategoryAndCourseState(Category category, CourseState courseState);

  Optional<Course> findByIdAndPrice(Long courseId, Long price);

  Optional<Course> findByIdAndCourseState(Long courseId, CourseState courseState);

}

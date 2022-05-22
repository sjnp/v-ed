package com.ved.backend.repo;

import com.ved.backend.model.*;
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

  interface CourseBasicInfo {
    Long getId();

    String getName();

    Long getPrice();

    String getPictureUrl();
  }

  CourseMaterials findCourseByInstructorAndCourseStateAndId(Instructor instructor, CourseState courseState, Long id);

  Course findCourseByCourseStateAndInstructorAndId(CourseState courseState, Instructor instructor, Long id);

  List<Course> findCoursesByCourseState(CourseState courseState);

  List<Course> findCoursesByCourseStateAndInstructor(CourseState courseState, Instructor instructor);

  Course findCourseByCourseStateAndId(CourseState courseState, Long id);

  List<CourseBasicInfo> findCoursesByInstructorAndCourseState(Instructor instructor, CourseState courseState);

  Course findCourseById(Long id);

  // move new refactor optional below
  List<Course> findCourseByCategoryAndCourseState(Category category, CourseState courseState);

  Optional<Course> findByIdAndPrice(Long courseId, Long price);

}

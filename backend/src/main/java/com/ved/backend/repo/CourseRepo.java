package com.ved.backend.repo;

import com.ved.backend.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Long> {

  Optional<Course> findByInstructorAndCourseStateAndId(Instructor instructor, CourseState courseState, Long id);

  List<Course> findCoursesByCourseState(CourseState courseState);

  List<Course> findCoursesByCourseStateAndInstructor(CourseState courseState, Instructor instructor);

  List<Course> findCoursesByCategoryAndCourseState(Category category, CourseState courseState);

  Optional<Course> findByIdAndPrice(Long courseId, Long price);

  Optional<Course> findByIdAndCourseState(Long courseId, CourseState courseState);
        
  interface SearchCourse {
    Long getCourseId();
    String getCourseName();
    String getPictureURL();
    String getInstructorName();
    String getCategory();
    Long getPrice();
    Double getRating();
    Long getReviewCount();
  }
  @Query(
    value =
      "SELECT " +
      "   c.id AS courseId, " +
      "   c.name AS courseName, " +
      "   c.picture_url AS pictureURL, " +
      "   CONCAT(s.first_name, ' ', s.last_name) AS instructorName, " +
      "   cg.name AS category, " +
      "   c.price AS price, " +
      "   pc.star AS rating, " +
      "   pc.total_user AS reviewCount " +
      "FROM " +
      "   course c " +
      "   INNER JOIN instructor_course ic ON ic.course_id = c.id " +
      "   INNER JOIN instructor i ON i.id = ic.instructor_id " +
      "   INNER JOIN student_instructor si ON si.instructor_id = i.id " +
      "   INNER JOIN student s ON s.id = si.student_id " +
      "   INNER JOIN course_course_state ccs ON ccs.course_id = c.id " +
      "   INNER JOIN course_state cs ON cs.id = ccs.course_state_id " +
      "   INNER JOIN course_category cc ON cc.course_id = c.id " +
      "   INNER JOIN category cg ON cg.id = cc.category_id " +
      "   INNER JOIN course_published_course cpc ON cpc.course_id = c.id " +
      "   INNER JOIN published_course pc ON pc.id = cpc.published_course_id " + 
      "WHERE " +
      "   c.name LIKE %:name% " +
      "   AND cs.name = 'PUBLISHED' " +
      "   AND cg.name IN (:category) " +
      "   AND c.price >= :minPrice " +
      "   AND c.price <= :maxPrice " +
      "   AND pc.star >= :rating ",
    nativeQuery = true
  )
  List<SearchCourse> search(String name, List<String> category, Long minPrice, Long maxPrice, Double rating);

}
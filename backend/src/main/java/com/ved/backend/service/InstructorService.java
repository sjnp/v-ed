package com.ved.backend.service;

import com.ved.backend.model.Course;
import com.ved.backend.repo.CourseRepo;

import java.util.HashMap;
import java.util.List;

public interface InstructorService {
  String getOmiseAccountData(String username);
  Long createCourse(Course course, String username);

  CourseRepo.CourseMaterials getIncompleteCourse(Long courseId, String username);

  String saveCoursePictureUrl(Long courseId, String objectName, String username);

  void updateCourseMaterials(Long courseId, Course course, String username);

  void deleteCoursePictureUrl(Long courseId, String username);

  void submitIncompleteCourse(Long courseId, String username);

  HashMap<String, Object> getAllIncompleteCourses(String username);

  HashMap<String, Object> getAllPendingCourses(String username);

  HashMap<String, Object> getAllApprovedCourses(String username);

  HashMap<String, Object> getAllRejectedCourses(String username);

  void publishApprovedCourse(Long courseId, String username);

  List<HashMap<String, Object>> getAllPublishedCourses(String username);
}
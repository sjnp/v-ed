package com.ved.backend.service;

import com.ved.backend.model.Course;
import com.ved.backend.repo.CourseRepo;

public interface InstructorService {
  String getOmiseAccountData(String username);
  Long createCourse(Course course, String username);
  CourseRepo.CourseMaterials getIncompleteCourse(Long courseId, String username);
  String saveCoursePictureUrl(Long courseId, String objectName, String username);
  void updateCourseMaterials(Long courseId, Course course, String username);
  void deleteCoursePictureUrl(Long courseId, String username);
  void submitIncompleteCourse(Long courseId, String username);
}
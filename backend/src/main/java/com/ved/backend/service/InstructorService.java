package com.ved.backend.service;

import com.ved.backend.model.AppUser;
import com.ved.backend.model.Course;

public interface InstructorService {
  void createCourse(Course course, String username);
}

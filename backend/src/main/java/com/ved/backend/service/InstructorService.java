package com.ved.backend.service;

import com.ved.backend.model.Course;

public interface InstructorService {

  Long createCourse(Course course, String username);

}
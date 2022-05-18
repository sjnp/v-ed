package com.ved.backend.exception;

import com.ved.backend.exception.baseException.NotFoundException;

public class CourseNotFoundException extends NotFoundException {

  public CourseNotFoundException(Long courseId) {

    super(String.format("Course id %s not found", courseId));
  }

}
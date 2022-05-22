package com.ved.backend.exception;

import com.ved.backend.exception.baseException.NotFoundException;

public class CourseStateNotFoundException extends NotFoundException {

  public CourseStateNotFoundException(String courseStateName) {
    super(String.format("Course state %s not found", courseStateName));
  }

}
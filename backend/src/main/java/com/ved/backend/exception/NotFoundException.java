package com.ved.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

  private static final Logger log = LoggerFactory.getLogger(NotFoundException.class);

  public NotFoundException(String msg) {
    super(HttpStatus.NOT_FOUND, msg);
    log.error(msg);
  }

  public static NotFoundException category(String categoryName) {
    String message = String.format("Category %s not found", categoryName);
    return new NotFoundException(message);
  }

  public static NotFoundException courseState(String courseStateName) {
    String message = String.format("Course state %s not found", courseStateName);
    return new NotFoundException(message);
  }
}

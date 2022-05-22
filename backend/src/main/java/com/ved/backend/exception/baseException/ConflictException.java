package com.ved.backend.exception.baseException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ConflictException extends ResponseStatusException {
  public ConflictException(String msg) {
    super(HttpStatus.CONFLICT, msg);
  }
}

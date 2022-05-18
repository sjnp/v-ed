package com.ved.backend.exception.baseException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedException extends ResponseStatusException {
  public UnauthorizedException(String msg) {
    super(HttpStatus.UNAUTHORIZED, msg);
  }
}

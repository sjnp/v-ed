package com.ved.backend.exception;

import com.ved.backend.exception.baseException.NotFoundException;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException(String username) {
    super("User: " + username + " not found");
  }
}

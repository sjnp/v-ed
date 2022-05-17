package com.ved.backend.exception;

import com.ved.backend.exception.baseException.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {

  public CategoryNotFoundException(String categoryName) {
    super(String.format("Category %s not found", categoryName));
  }

}
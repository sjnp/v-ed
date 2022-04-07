package com.ved.backend.service;

// import com.ved.backend.exception.RegisterException;
import com.ved.backend.model.AppUser;

import org.hibernate.exception.ConstraintViolationException;

public interface AppUserService {
  AppUser registerStudent(AppUser appUser) throws ConstraintViolationException;
  AppUser getAppUser(String username);
}

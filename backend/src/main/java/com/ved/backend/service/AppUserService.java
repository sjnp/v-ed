package com.ved.backend.service;

import com.ved.backend.exception.RegisterException;
import com.ved.backend.model.AppUser;

public interface AppUserService {
  AppUser registerStudent(AppUser appUser) throws RegisterException;
  AppUser getAppUser(String username);
  void changeRoleFromStudentIntoInstructor(String username);
}

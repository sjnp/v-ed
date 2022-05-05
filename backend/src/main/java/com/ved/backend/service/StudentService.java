package com.ved.backend.service;

import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import com.ved.backend.storeClass.Finance;

public interface StudentService {
  Student getStudent(String username);
  void changeRoleFromStudentIntoInstructor(Instructor instructor, String username);
  String activeInstructor(Finance finance, String username);

}

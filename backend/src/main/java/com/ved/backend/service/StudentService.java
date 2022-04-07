package com.ved.backend.service;

import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;

public interface StudentService {
  Student getStudent(String username);
  void changeRoleFromStudentIntoInstructor(Instructor instructor, String username);

}

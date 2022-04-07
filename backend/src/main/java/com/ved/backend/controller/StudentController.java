package com.ved.backend.controller;

import com.ved.backend.model.Instructor;
import com.ved.backend.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api/students")
public class StudentController {
  private final StudentService studentService;

  @PutMapping(path = "/instructor-feature")
  public ResponseEntity<?> changeStudentIntoInstructor(@RequestBody Instructor instructor, Principal principal) {
    studentService.changeRoleFromStudentIntoInstructor(instructor, principal.getName());
    return ResponseEntity.ok().build();
  }

  public StudentController(final StudentService studentService) {
    this.studentService = studentService;
  }
}

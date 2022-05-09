package com.ved.backend.controller;

import com.ved.backend.model.Instructor;
import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.service.OverviewService;
import com.ved.backend.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/students")
public class StudentController {

  private final StudentService studentService;
  private final OverviewService overviewService;

  @PutMapping(path = "/instructor-feature")
  public ResponseEntity<?> changeStudentIntoInstructor(@RequestBody Instructor instructor, Principal principal) {
    studentService.changeRoleFromStudentIntoInstructor(instructor, principal.getName());
    return ResponseEntity.ok().build();
  }

  @GetMapping("/my-course")
  public ResponseEntity<ArrayList<CourseCardResponse>> getMyCourse(Principal principal) {
    // fix latter.
    ArrayList<CourseCardResponse> response = overviewService.getOverviewMyCourse(principal.getName());
    return ResponseEntity.ok().body(response);
  }

  public StudentController(final StudentService studentService, final OverviewService overviewService) {
    this.studentService = studentService;
    this.overviewService = overviewService;
  }
}

package com.ved.backend.controller;

import com.ved.backend.model.Instructor;
import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.service.OverviewService;
import com.ved.backend.service.StudentService;
import com.ved.backend.storeClass.Finance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ArrayList<CourseCardResponse> response = overviewService.getOverviewMyCouese(principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @PostMapping(path = "/active-instrustor")
  public ResponseEntity<?> activeInstructor(@RequestBody Finance finance, Principal principal) {
    String result = studentService.activeInstructor(finance, principal.getName());

    return ResponseEntity.ok(result);
  }

  public StudentController(final StudentService studentService, final OverviewService overviewService) {
    this.studentService = studentService;
    this.overviewService = overviewService;
  }
}

package com.ved.backend.controller;

import com.ved.backend.model.Course;
import com.ved.backend.service.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping(path = "/api/instructors")
public class InstructorController {
  private final InstructorService instructorService;

  @PostMapping(path = "/course")
  public ResponseEntity<?> createCourse(@RequestBody Course course, Principal principal) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
    instructorService.createCourse(course, principal.getName());
    return ResponseEntity.created(uri).body(null);
  }

  public InstructorController(InstructorService instructorService) {
    this.instructorService = instructorService;
  }
}

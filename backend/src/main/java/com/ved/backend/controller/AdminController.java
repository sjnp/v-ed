package com.ved.backend.controller;

import com.ved.backend.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/admins")
public class AdminController {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdminController.class);

  private final CourseService courseService;

  @GetMapping(path = "/pending-courses")
  public ResponseEntity<?> getPendingCourses() {
    List<Map<String,Object>> pendingCoursesJson = courseService.getPendingCourse();
    return ResponseEntity.ok().body(pendingCoursesJson);
  }

  public AdminController(CourseService courseService) {
    this.courseService = courseService;
  }
}

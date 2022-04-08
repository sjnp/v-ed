package com.ved.backend.controller;

import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.service.CourseStateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/course-states")
public class CourseStateController {

  private final CourseStateService courseStateService;

  @GetMapping("/")
  public Collection<CourseStateRepo.IdAndNameOnly> getAllCourseStates() {
    return courseStateService.getAllCourseStates();
  }

  public CourseStateController(CourseStateService courseStateService) {
    this.courseStateService = courseStateService;
  }
}

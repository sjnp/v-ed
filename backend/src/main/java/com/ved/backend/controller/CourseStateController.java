package com.ved.backend.controller;

import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/course-states")
public class CourseStateController {

  private final CourseService courseService;

  @GetMapping("")
  public Collection<CourseStateRepo.IdAndNameOnly> getAllCourseStates() {
    return courseService.getAllCourseStates();
  }

}

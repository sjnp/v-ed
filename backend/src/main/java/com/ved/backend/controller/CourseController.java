package com.ved.backend.controller;

import com.ved.backend.response.CourseResponse;
import com.ved.backend.service.CourseService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;
    
    public CourseController(final CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable Long courseId) {
        CourseResponse response = courseService.getCourse(courseId);
        return ResponseEntity.ok().body(response);
    }

}
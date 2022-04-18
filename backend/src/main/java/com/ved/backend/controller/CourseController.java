package com.ved.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    
    @GetMapping("/{courseId}")
    public void getCourse(@PathVariable Long courseId) {
        
        // todo latter.
    }

}
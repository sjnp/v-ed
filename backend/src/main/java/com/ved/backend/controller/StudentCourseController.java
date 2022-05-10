package com.ved.backend.controller;

import java.security.Principal;

import com.ved.backend.service.StudentCourseService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student-course")
public class StudentCourseController {

    private final StudentCourseService studentCourseService;
 
    public StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    @PostMapping("/buy-free-course/{courseId}")
    public ResponseEntity<?> buyFreeCourse(@PathVariable Long courseId, Principal principal) {
        studentCourseService.buyFreeCourse(courseId, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
package com.ved.backend.controller;

import java.security.Principal;
import java.util.ArrayList;

import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.response.OverviewResponse;
import com.ved.backend.service.OverviewService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    private final OverviewService overviewService;
 
    public OverviewController(OverviewService overviewService) {
        this.overviewService = overviewService;
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ArrayList<CourseCardResponse>> getOverviewCategory(@PathVariable String category) {

        ArrayList<CourseCardResponse> response = overviewService.getOverviewCategory(category);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/my-course")
    public ResponseEntity<ArrayList<CourseCardResponse>> getOverviewMyCouese(Principal principal) {
        
        ArrayList<CourseCardResponse> response = overviewService.getOverviewMyCouese(principal.getName());
        return ResponseEntity.ok().body(response); 
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<OverviewResponse> getOverviewCourse(@PathVariable Long courseId) {
        
        OverviewResponse response = overviewService.getOverviewCourse(courseId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/course/{courseId}/card")
    public ResponseEntity<CourseCardResponse> getOverviewCourseCard(@PathVariable Long courseId) {
        
        CourseCardResponse response = overviewService.getOverviewCourseCard(courseId);
        return ResponseEntity.ok().body(response);
    }
}
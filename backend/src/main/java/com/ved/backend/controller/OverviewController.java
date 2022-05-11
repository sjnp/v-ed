package com.ved.backend.controller;

import java.security.Principal;
import java.util.ArrayList;

import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.response.OverviewResponse;
import com.ved.backend.service.OverviewService;
import com.ved.backend.service.PrivateObjectStorageService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/overviews")
public class OverviewController {

    private final OverviewService overviewService;
    private final PrivateObjectStorageService privateObjectStorageService;

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ArrayList<CourseCardResponse>> getOverviewCategory(@PathVariable String categoryName) {

        ArrayList<CourseCardResponse> response = overviewService.getOverviewCategory(categoryName);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<OverviewResponse> getOverviewCourse(@PathVariable Long courseId) {
        
        OverviewResponse response = overviewService.getOverviewCourse(courseId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/courses/{courseId}/card")
    public ResponseEntity<CourseCardResponse> getOverviewCourseCard(@PathVariable Long courseId) {
        
        CourseCardResponse response = overviewService.getOverviewCourseCard(courseId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/video-example/{fileName}")
    public ResponseEntity<String> getVideoExampleURI(@PathVariable String fileName) {
        
        String response = privateObjectStorageService.getAccessURI(fileName);
        return ResponseEntity.ok().body(response);
    }
    
}
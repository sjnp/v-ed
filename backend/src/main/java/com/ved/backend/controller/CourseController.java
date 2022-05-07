package com.ved.backend.controller;

import com.ved.backend.response.CourseResponse;
import com.ved.backend.service.CourseService;
import com.ved.backend.service.PrivateObjectStorageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;
    private final PrivateObjectStorageService privateObjectStorageService;
    
    public CourseController(final CourseService courseService, final PrivateObjectStorageService privateObjectStorageService) {
        this.courseService = courseService;
        this.privateObjectStorageService = privateObjectStorageService;
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable Long courseId) {
        CourseResponse response = courseService.getCourse(courseId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/video/{fileName}")
    public ResponseEntity<String> getVideoExampleURI(@PathVariable String fileName) {
        String response = privateObjectStorageService.getAccessURI(fileName);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{courseId}/video/chapter/{chapterNo}/section/{sectionNo}")
    public ResponseEntity<String> getVideoURI(
        @PathVariable String courseId, 
        @PathVariable String chapterNo,
        @PathVariable String sectionNo
    ) {
        String response = privateObjectStorageService.getAccessVideoURI(courseId, chapterNo, sectionNo);
        return ResponseEntity.ok().body(response);
    }

}
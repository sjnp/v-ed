package com.ved.backend.controller;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.response.OverviewResponse;
import com.ved.backend.service.CourseService;
import com.ved.backend.service.PublicService;

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

    private final PublicService publicService;
    private final CourseService courseService;

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<CourseCardResponse>> getOverviewCategory(@PathVariable String categoryName) {
        List<CourseCardResponse> response = publicService.getOverviewByCategory(categoryName);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<OverviewResponse> getOverviewCourse(@PathVariable Long courseId, Principal principal) {
        OverviewResponse response;
        if (Objects.isNull(principal)) {
            response = publicService.getOverviewCourse(courseId);
        } else {
            response = courseService.getOverviewCourse(courseId, principal.getName());
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/video-example/courses/{courseId}")
    public ResponseEntity<String> getVideoExampleUrl(@PathVariable Long courseId) {
        String response = publicService.getVideoExampleUrl(courseId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/courses/{courseId}/card")
    public ResponseEntity<CourseCardResponse> getCourseCard(@PathVariable Long courseId) {
        CourseCardResponse response = publicService.getCourseCard(courseId);
        return ResponseEntity.ok().body(response);
    }
    
}
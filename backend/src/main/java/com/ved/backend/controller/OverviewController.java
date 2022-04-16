package com.ved.backend.controller;

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

    @GetMapping("/{courseId}")
    public ResponseEntity<OverviewResponse> getOverviewCourse(@PathVariable Long courseId) {

        OverviewResponse overviewResponse = overviewService.getOverviewCourse(courseId);
        return ResponseEntity.ok().body(overviewResponse);
    }

}
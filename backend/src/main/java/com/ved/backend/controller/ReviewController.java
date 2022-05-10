package com.ved.backend.controller;

import java.security.Principal;

import com.ved.backend.request.ReviewRequest;
import com.ved.backend.response.ReviewCourseResponse;
import com.ved.backend.response.ReviewResponse;
import com.ved.backend.service.ReviewService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
 
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest reviewRequest, Principal principal) {
        reviewService.create(reviewRequest, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ReviewCourseResponse> getReviewCourse(@PathVariable Long courseId, Principal principal) {
        ReviewCourseResponse response = reviewService.getReviewCourse(courseId, principal.getName());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long reviewId) {
        ReviewResponse response = reviewService.getReview(reviewId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{reviewId}/edit")
    public ResponseEntity<?> editReview(@PathVariable Long reviewId, @RequestBody ReviewRequest reviewRequest) {
        reviewService.edit(reviewId, reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
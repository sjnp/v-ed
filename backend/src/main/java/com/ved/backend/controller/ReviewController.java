package com.ved.backend.controller;

import com.ved.backend.model.Review;
import com.ved.backend.response.ReviewResponse;
import com.ved.backend.service.ReviewService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<ReviewResponse> createReview(@RequestBody Review review) {
        ReviewResponse response = reviewService.create(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
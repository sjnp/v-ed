package com.ved.backend.service;

import java.time.LocalDateTime;

import com.ved.backend.model.Review;
import com.ved.backend.repo.ReviewRepo;
import com.ved.backend.response.ReviewResponse;

import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepo;
    
    public ReviewServiceImpl(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    public ReviewResponse create(Review review) {
        
        review.setReviewDateTime(LocalDateTime.now());
        review.setVisible(true);

        Review response = reviewRepo.save(review);

        return new ReviewResponse(response);
    }
}
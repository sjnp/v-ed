package com.ved.backend.service;

import com.ved.backend.model.Review;
import com.ved.backend.response.ReviewResponse;

public interface ReviewService {
 
    public ReviewResponse create(Review review);
}
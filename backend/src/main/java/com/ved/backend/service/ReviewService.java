package com.ved.backend.service;

import com.ved.backend.request.ReviewRequest;
import com.ved.backend.response.ReviewCourseResponse;
import com.ved.backend.response.ReviewResponse;

public interface ReviewService {
 
    public void create(ReviewRequest reviewRequest, String username);

    public ReviewCourseResponse getReviewCourse(Long courseId, String username);

    public ReviewResponse getReview(Long reviewId);

    public void edit(Long reviewId, ReviewRequest reviewRequest);

}
package com.ved.backend.response;

import java.time.LocalDateTime;

import com.ved.backend.model.Review;

public class ReviewResponse {
 
    private Long id;
    private double rating;
    private String comment;
    private LocalDateTime reviewDateTime;
    private boolean visible;

    public ReviewResponse() {
    }
    
    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.reviewDateTime = review.getReviewDateTime();
        this.visible = review.isVisible();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getReviewDateTime() {
        return reviewDateTime;
    }

    public void setReviewDateTime(LocalDateTime reviewDateTime) {
        this.reviewDateTime = reviewDateTime;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
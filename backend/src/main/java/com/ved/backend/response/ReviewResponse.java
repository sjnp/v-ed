package com.ved.backend.response;

import java.time.LocalDateTime;

import com.ved.backend.model.Review;

public class ReviewResponse {
 
    private Long id;
    private double rating;
    private String comment;
    private String firstname;
    private String lastname;
    private LocalDateTime reviewDateTime;
    private boolean visible;

    public ReviewResponse() {
    }
    
    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.firstname = review.getStudent().getFirstName();
        this.lastname= review.getStudent().getLastName();
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
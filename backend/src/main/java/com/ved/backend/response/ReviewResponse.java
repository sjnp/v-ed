package com.ved.backend.response;

import java.time.LocalDateTime;

import com.ved.backend.model.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReviewResponse {
 
    private Long id;
    
    private double rating;
    
    private String comment;

    private String reviewUsername;

    private String displayUrl;
    
    private String firstname;
    
    private String lastname;
    
    private LocalDateTime reviewDateTime;
    
    private boolean visible;
    
    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.reviewUsername = review.getStudent().getAppUser().getUsername();
        this.displayUrl = review.getStudent().getProfilePicUri();
        this.firstname = review.getStudent().getFirstName();
        this.lastname= review.getStudent().getLastName();
        this.reviewDateTime = review.getReviewDateTime();
        this.visible = review.isVisible();
    }

}
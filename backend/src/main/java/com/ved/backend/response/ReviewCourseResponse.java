package com.ved.backend.response;

import java.util.List;

public class ReviewCourseResponse {

    private Long courseId;
    private Long myReviewId;
    private Boolean isReview;
    private Double star;
    private Long totalReviewUser;
    private List<ReviewResponse> reviews;

    public ReviewCourseResponse() {
    }

    public ReviewCourseResponse(
        Long courseId,
        Long myReviewId,
        Boolean isReview,
        Double star,
        Long totalReviewUser,
        List<ReviewResponse> reviews
    ) {
        this.courseId = courseId;
        this.myReviewId = myReviewId;
        this.isReview = isReview;
        this.star = star;
        this.totalReviewUser = totalReviewUser;
        this.reviews = reviews;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getMyReviewId() {
        return myReviewId;
    }

    public void setMyReviewId(Long myReviewId) {
        this.myReviewId = myReviewId;
    }

    public Boolean getIsReview() {
        return isReview;
    }

    public void setIsReview(Boolean isReview) {
        this.isReview = isReview;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public Long getTotalReviewUser() {
        return totalReviewUser;
    }

    public void setTotalReviewUser(Long totalReviewUser) {
        this.totalReviewUser = totalReviewUser;
    }

    public List<ReviewResponse> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewResponse> reviews) {
        this.reviews = reviews;
    }

}
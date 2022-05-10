package com.ved.backend.response;

import java.util.List;

import com.ved.backend.model.Chapter;
import com.ved.backend.model.Course;
import com.ved.backend.model.Review;

public class OverviewResponse {

    private long courseId;
    private String courseName;
    private long price;
    private String pictureURL;
    private Double ratingCourse;
    private Long totalReview;

    private String overview;
    private List<Chapter> chapterList;
    private String requirement;
    private List<Review> reviewList;
    
    private String instructorFirstname;
    private String instructorLastname;
    private String instructorPictureURI;
    private String biography;
    private String occupation;

    public OverviewResponse() {
    }

    public OverviewResponse(Course course) {
        this.courseId = course.getId();
        this.courseName = course.getName();
        this.price = course.getPrice();
        this.pictureURL = course.getPictureUrl();
        this.ratingCourse = course.getPublishedCourse().getStar();
        this.totalReview = course.getPublishedCourse().getTotalUser();
        this.overview = course.getOverview();
        this.chapterList = course.getChapters();
        this.requirement = course.getRequirement();
        this.reviewList = course.getPublishedCourse().getReviews();
        this.instructorFirstname = course.getInstructor().getStudent().getFirstName();
        this.instructorLastname = course.getInstructor().getStudent().getLastName();
        this.instructorPictureURI = course.getInstructor().getStudent().getProfilePicUri();
        this.biography = course.getInstructor().getStudent().getBiography();
        this.occupation = course.getInstructor().getStudent().getOccupation();
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public String getInstructorFirstname() {
        return instructorFirstname;
    }

    public void setInstructorFirstname(String instructorFirstname) {
        this.instructorFirstname = instructorFirstname;
    }

    public String getInstructorLastname() {
        return instructorLastname;
    }

    public void setInstructorLastname(String instructorLastname) {
        this.instructorLastname = instructorLastname;
    }

    public String getInstructorPictureURI() {
        return instructorPictureURI;
    }

    public void setInstructorPictureURI(String instructorPictureURI) {
        this.instructorPictureURI = instructorPictureURI;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public Double getRatingCourse() {
        return ratingCourse;
    }

    public void setRatingCourse(Double ratingCourse) {
        this.ratingCourse = ratingCourse;
    }

    public Long getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(Long totalReview) {
        this.totalReview = totalReview;
    }
    
}
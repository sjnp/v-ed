package com.ved.backend.model;

public class PreviewModel {

    private String courseName;
    private String instructorName;
    private double rating;
    private int reviewTotal;
    private String imageCourseURL;
    
    public PreviewModel() {}

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviewTotal() {
        return reviewTotal;
    }

    public void setReviewTotal(int reviewTotal) {
        this.reviewTotal = reviewTotal;
    }

    public String getImageCourseURL() {
        return imageCourseURL;
    }

    public void setImageCourseURL(String imageCourseURL) {
        this.imageCourseURL = imageCourseURL;
    }
    
}
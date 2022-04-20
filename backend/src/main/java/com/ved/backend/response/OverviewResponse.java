package com.ved.backend.response;

import java.util.List;

import com.ved.backend.model.Chapter;

public class OverviewResponse {

    private long courseId;
    private String courseName;
    private long price;

    private String overview;
    private List<Chapter> chapterList;
    private String requirement;
    private List<String> reviewList;
    
    private String instructorFirstname;
    private String instructorLastname;
    private String instructorPictureURI;
    private String biography;
    private String occupation;

    public OverviewResponse() {}

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

    public List<String> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<String> reviewList) {
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
    
}
package com.ved.backend.response;

import com.ved.backend.model.Course;

public class AboutCourseResponse {
 
    private Long courseId;
    private String overview;
    private String requirement;

    public AboutCourseResponse() {
    }

    public AboutCourseResponse(Course course) {
        this.courseId = course.getId();
        this.overview = course.getOverview();
        this.requirement = course.getRequirement();
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }
    
}
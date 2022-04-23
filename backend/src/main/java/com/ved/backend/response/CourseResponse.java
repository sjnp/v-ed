package com.ved.backend.response;

import java.util.List;

import com.ved.backend.model.Chapter;

public class CourseResponse {
 
    private Long courseId;
    private List<Chapter> content;

    public CourseResponse() {}

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public List<Chapter> getContent() {
        return content;
    }

    public void setContent(List<Chapter> content) {
        this.content = content;
    }

}
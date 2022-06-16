package com.ved.backend.response;

import com.ved.backend.model.Course;

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
public class AboutCourseResponse {
 
    private Long courseId;

    private String overview;
    
    private String requirement;

    public AboutCourseResponse(Course course) {
        this.courseId = course.getId();
        this.overview = course.getOverview();
        this.requirement = course.getRequirement();
    }
    
}
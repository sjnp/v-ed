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

    private String profilePictureUrl;

    private String instructorName;

    private String biography;

    private String occupation;

    private String overview;
    
    private String requirement;

    public AboutCourseResponse(Course course) {
        this.courseId = course.getId();
        this.profilePictureUrl = course.getInstructor().getStudent().getProfilePicUri();
        this.instructorName = course.getInstructor().getStudent().getFullName();
        this.biography = course.getInstructor().getStudent().getBiography();
        this.occupation = course.getInstructor().getStudent().getOccupation();
        this.overview = course.getOverview();
        this.requirement = course.getRequirement();
    }
    
}
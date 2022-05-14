package com.ved.backend.response;

import com.ved.backend.model.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCardResponse {
 
    private long courseId;

    private String courseName;

    private String instructorName;

    private double rating;

    private Long reviewCount;

    private String pictureURL;

    private long price;

    public CourseCardResponse(Course course) {
        this.courseId = course.getId();
        this.courseName = course.getName();
        this.instructorName = course.getInstructor().getStudent().getFullName();
        this.rating = course.getPublishedCourse().getStar();
        this.reviewCount = course.getPublishedCourse().getTotalUser();
        this.pictureURL = course.getPictureUrl();
        this.price = course.getPrice();
    }
    
}
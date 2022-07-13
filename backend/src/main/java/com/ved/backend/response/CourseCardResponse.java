package com.ved.backend.response;

import com.ved.backend.model.Course;
import com.ved.backend.repo.CourseRepo.SearchCourse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCardResponse {
 
    private long courseId;

    private String courseName;

    private String instructorName;

    private double rating;

    private Long reviewCount;

    private String pictureURL;

    private long price;

    private String category;

    public CourseCardResponse(Course course) {
        this.courseId = course.getId();
        this.courseName = course.getName();
        this.instructorName = course.getInstructor().getStudent().getFullName();
        this.rating = course.getPublishedCourse().getStar();
        this.reviewCount = course.getPublishedCourse().getTotalUser();
        this.pictureURL = course.getPictureUrl();
        this.price = course.getPrice();
        this.category = course.getCategory().getName();
    }

    public CourseCardResponse(SearchCourse searchCourse) {
        this.courseId = searchCourse.getCourseId();
        this.courseName = searchCourse.getCourseName();
        this.instructorName = searchCourse.getInstructorName();
        this.rating = searchCourse.getRating();
        this.reviewCount = searchCourse.getReviewCount();
        this.pictureURL = searchCourse.getPictureURL();
        this.price = searchCourse.getPrice();
        this.category = searchCourse.getCategory();
    }
    
}
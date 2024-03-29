package com.ved.backend.response;

import java.util.List;
import java.util.stream.Collectors;

import com.ved.backend.model.Chapter;
import com.ved.backend.model.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OverviewResponse {

    private Long courseId;
    private String courseName;
    private long price;
    private String pictureURL;
    private Double ratingCourse;
    private Long totalReview;

    private String overview;
    private List<Chapter> chapterList;
    private String requirement;
    private List<ReviewResponse> reviewList;
    
    private String instructorFirstname;
    private String instructorLastname;
    private String instructorPictureURI;
    private String biography;
    private String occupation;

    private String stateOfCourse;

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
        this.reviewList = course.getPublishedCourse().getReviews()
            .stream()
            .filter(review -> review.isVisible() == true)
            .map(review -> new ReviewResponse(review))
            .collect(Collectors.toList());
        this.instructorFirstname = course.getInstructor().getStudent().getFirstName();
        this.instructorLastname = course.getInstructor().getStudent().getLastName();
        this.instructorPictureURI = course.getInstructor().getStudent().getProfilePicUri();
        this.biography = course.getInstructor().getStudent().getBiography();
        this.occupation = course.getInstructor().getStudent().getOccupation();
    }
    
}
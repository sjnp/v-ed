package com.ved.backend.response;

import com.ved.backend.model.PublishedCourse;

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
public class PublishedCourseResponse {
    
    private Long id;

    private Double totalScore;

    private Long totalUser;

    private Double star;

    public PublishedCourseResponse(PublishedCourse publishedCourse) {
        this.id = publishedCourse.getId();
        this.totalScore = publishedCourse.getTotalScore();
        this.totalUser = publishedCourse.getTotalUser();
        this.star = publishedCourse.getStar();
    }

}
package com.ved.backend.response;

import java.util.List;

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
public class ReviewCourseResponse {

    private PublishedCourseResponse summary;

    private List<ReviewResponse> reviews;

    private Long myReviewId;

}
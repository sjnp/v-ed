package com.ved.backend.service;

import java.util.ArrayList;

import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.response.OverviewResponse;

public interface OverviewService {

    public ArrayList<CourseCardResponse> getOverviewCategory(String categoryName);

    // TODO: move this to StudentService
    public ArrayList<CourseCardResponse> getOverviewMyCourse(String username);
    
    public OverviewResponse getOverviewCourse(Long courseId);
    
    public CourseCardResponse getOverviewCourseCard(Long courseId);

}
package com.ved.backend.service;

import com.ved.backend.model.CourseState;
import com.ved.backend.response.CourseResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CourseService {
 
    public CourseResponse getCourse(Long courseId);

    public List<Map<String,Object>> getPendingCourses();

    Map<String, Object> getPendingCourse(Long courseId);
    public String getVideoUriFromPendingCourse(Long courseId,
                                               Integer chapterIndex,
                                               Integer sectionIndex,
                                               String videoUri,
                                               String username);

    public String getHandoutUrlFromPendingCourse(Long courseId,
                                               Integer chapterIndex,
                                               Integer sectionIndex,
                                               String handoutUri,
                                               String username);


    public void changePendingCourseState(Long courseId, Boolean isApproved);
}
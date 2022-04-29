package com.ved.backend.service;

import com.ved.backend.model.CourseState;
import com.ved.backend.response.CourseResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CourseService {
 
    public CourseResponse getCourse(Long courseId);
    public List<Map<String,Object>> getPendingCourse();
}
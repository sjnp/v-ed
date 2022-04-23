package com.ved.backend.service;

import java.util.List;

import com.ved.backend.model.Chapter;
import com.ved.backend.model.Course;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.response.CourseResponse;
// import com.ved.backend.response.CourseResponse.ChapterStudent;

import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;
 
    public CourseServiceImpl(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    public CourseResponse getCourse(Long courseId) {
        Course course = courseRepo.getById(courseId);

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setCourseId(course.getId());
        courseResponse.setContent(course.getChapters());
        
        return courseResponse;
    }
    
}
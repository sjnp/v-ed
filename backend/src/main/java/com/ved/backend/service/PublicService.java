package com.ved.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.response.OverviewResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class PublicService {

    private final CategoryService categoryService;
    private final CourseStateService courseStateService;
    private final CourseService courseService;
    private final PrivateObjectStorageService privateObjectStorageService;

    private final CourseStateProperties courseStateProperties;
    private static final Logger log = LoggerFactory.getLogger(PublicService.class);

    public List<CourseCardResponse> getOverviewByCategory(String categoryName) {
        log.info("Get overview by category {}", categoryName);
        Category category = categoryService.getByName(categoryName);
        CourseState courseState = courseStateService.getByName(courseStateProperties.getPublished());
        List<Course> courses = courseService.getByCategoryAndCourseState(category, courseState);
        return courses
            .stream()
            .map((c) -> new CourseCardResponse(c))
            .collect(Collectors.toList());
    }

    public OverviewResponse getOverviewCourse(Long courseId) {
        log.info("Get course id {}", courseId);
        Course course = courseService.getById(courseId);
        return new OverviewResponse(course);
    }

    public String getVideoExampleUrl(Long courseId) {
        String fileName = String.format("course_vid_%s_c0_s0.mp4", courseId);
        String username = "public_overview";
        return privateObjectStorageService.readFile(fileName, username);
    }

    public CourseCardResponse getOverviewCourseCard(Long courseId) {
        log.info("Get course card by id {}", courseId);
        Course course = courseService.getById(courseId);
        return new CourseCardResponse(course);
    }

}
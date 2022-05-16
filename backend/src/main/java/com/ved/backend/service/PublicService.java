package com.ved.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.exception.NotFoundException;
import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.response.CourseCardResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class PublicService {

    private final CategoryRepo categoryRepo;
    private final CourseStateRepo courseStateRepo;
    private final CourseRepo courseRepo;

    private final CourseStateProperties courseStateProperties;

    private static final Logger log = LoggerFactory.getLogger(PublicService.class);

    public List<CourseCardResponse> getOverviewCategory(String categoryName) {

        log.info("Get overview category {}", categoryName);

        Category category = categoryRepo.findByName(categoryName)
            .orElseThrow(() -> {
                String message = String.format("Category %s not found.", categoryName);
                log.error(message);
                return new NotFoundException(message);
            });

        CourseState courseState = courseStateRepo.findCourseStateByName(courseStateProperties.getPublished())
            .orElseThrow(() -> {
                String message = "Course state PUBLISHED not found.";
                log.error(message);
                return new NotFoundException(message);
            });

        List<Course> courses = courseRepo.findCourseByCategoryAndCourseState(category, courseState);
            // .orElseThrow(() -> {
            //     String message = String.format("Course category %s not found.", categoryName);
            //     log.error(message);
            //     return new NotFoundException(message);
            // });

        List<CourseCardResponse> response = courses.stream()
            .map((course) -> new CourseCardResponse(course))
            .collect(Collectors.toList());

        return response;
    }

    public void getOverviewCourse(Long courseId) {
        
    }

    public void getCourseCard(Long courseId) {
        
    }

}
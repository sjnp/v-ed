package com.ved.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.response.OverviewResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OverviewServiceImpl implements OverviewService {

    private CategoryRepo categoryRepo;
    private CourseRepo courseRepo;

    public OverviewServiceImpl(CategoryRepo categoryRepo, CourseRepo courseRepo) {
        this.categoryRepo = categoryRepo;
        this.courseRepo = courseRepo;
    }

    private ArrayList<CourseCardResponse> getCourseCardResponseList(Collection<Course> courses) {

        ArrayList<CourseCardResponse> courseCardResponseList = new ArrayList<CourseCardResponse>();
        for (Course course : courses) {

            if (Objects.isNull(course.getPublishedCourse())) continue;

            CourseCardResponse courseCardResponse = this.getCourseCardResponse(course);
            courseCardResponseList.add(courseCardResponse);
        }

        return courseCardResponseList;
    }

    private CourseCardResponse getCourseCardResponse(Course course) {
    
        CourseCardResponse courseCardResponse = new CourseCardResponse();
        courseCardResponse.setCourseId(course.getId());
        courseCardResponse.setCourseName(course.getName());
        String instructorFirstName = course.getInstructor().getStudent().getFirstName();
        String instructorLastName = course.getInstructor().getStudent().getLastName();
        courseCardResponse.setInstructorName(instructorFirstName + " " + instructorLastName);
        courseCardResponse.setPictureURL(course.getPictureUrl());
        courseCardResponse.setPrice(course.getPrice());
        courseCardResponse.setRating(course.getPublishedCourse().getStar());
        courseCardResponse.setReviewCount(course.getPublishedCourse().getTotalUser());

        return courseCardResponse;
    }

    public ArrayList<CourseCardResponse> getOverviewCategory(String categoryName) {

        Category category = this.categoryRepo.findByName(categoryName.toUpperCase());
        Set<Course> courseSet = category.getCourses();
        ArrayList<CourseCardResponse> courseCardResponses = getCourseCardResponseList(courseSet);

        return courseCardResponses;
    }

    public ArrayList<CourseCardResponse> getOverviewMyCourse(String username) {

        List<Course> courses = courseRepo.findAll(); // test find all, todo find from student course later.
        ArrayList<CourseCardResponse> courseCardResponseList = getCourseCardResponseList(courses);

        return courseCardResponseList;
    }
    
    public OverviewResponse getOverviewCourse(Long courseId) {

        Optional<Course> courseOptional = courseRepo.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new MyException("overview.course.id.not.found", HttpStatus.NOT_FOUND);
        }

        Course course = courseOptional.get();
        OverviewResponse overviewResponse = new OverviewResponse(course);

        return overviewResponse;
    }
    
    public CourseCardResponse getOverviewCourseCard(Long courseId) {

        Optional<Course> courseOptional = courseRepo.findById(courseId);

        if (!courseOptional.isPresent()) {
            throw new MyException("overview.course.id.not.found", HttpStatus.NOT_FOUND);
        }

        Course course = courseOptional.get();
        CourseCardResponse courseCardResponse = this.getCourseCardResponse(course);

        return courseCardResponse;
    }

}
package com.ved.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        courseCardResponse.setRating(4.5); // hard code, fix latter.
        courseCardResponse.setReviewCount(20); // hard code, fix latter.

        return courseCardResponse;
    }

    public ArrayList<CourseCardResponse> getOverviewCategory(String categoryName) {

        Category category = this.categoryRepo.findByName(categoryName);
        ArrayList<CourseCardResponse> courseCardResponseList = getCourseCardResponseList(category.getCourses());

        return courseCardResponseList;
    }

    public ArrayList<CourseCardResponse> getOverviewMyCouese(String username) {

        List<Course> courses = courseRepo.findAll(); // test find all, todo latter.
        ArrayList<CourseCardResponse> courseCardResponseList = getCourseCardResponseList(courses);

        return courseCardResponseList;
    }
    
    public OverviewResponse getOverviewCourse(Long courseId) {

        Optional<Course> courseOptional = courseRepo.findById(courseId);

        if (!courseOptional.isPresent()) {
            throw new MyException("overview.course.id.not.found", HttpStatus.NOT_FOUND);
        }

        Course course = courseOptional.get();
        OverviewResponse overviewResponse = new OverviewResponse();
        overviewResponse.setCourseId(course.getId());
        overviewResponse.setCourseName(course.getName());
        overviewResponse.setPrice(course.getPrice());
        overviewResponse.setPictureURL(course.getPictureUrl());
        overviewResponse.setOverview(course.getOverview());
        overviewResponse.setChapterList(course.getChapters());
        overviewResponse.setRequirement(course.getRequirement());
        overviewResponse.setReviewList(null);
        overviewResponse.setInstructorFirstname(course.getInstructor().getStudent().getFirstName());
        overviewResponse.setInstructorLastname(course.getInstructor().getStudent().getLastName());
        overviewResponse.setInstructorPictureURI(course.getInstructor().getStudent().getProfilePicUri());
        overviewResponse.setBiography(course.getInstructor().getStudent().getBiography());
        overviewResponse.setOccupation(course.getInstructor().getStudent().getOccupation());

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
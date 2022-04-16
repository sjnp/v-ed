package com.ved.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.PreviewRepo;
import com.ved.backend.response.PreviewResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PreviewServiceImpl implements PreviewService {

    private final PreviewRepo previewRepo;
    private final CategoryRepo categoryRepo;

    public PreviewServiceImpl(PreviewRepo previewRepo, CategoryRepo categoryRepo) {
        this.previewRepo = previewRepo;
        this.categoryRepo = categoryRepo;
    }

    public ArrayList<PreviewResponse> getPreviewCategory(String categoryName) {

        Category category = categoryRepo.findByName(categoryName);

        if (category == null) {
            throw new RuntimeException("ERROR: category not match.");
        }

        ArrayList<PreviewResponse> previewResponseList = this.getPreviewResponseList(category.getCourses());

        return previewResponseList;
    }

    public ArrayList<PreviewResponse> getPreviewMyCourse() {
        
        ArrayList<Course> courses = previewRepo.findAll();
        ArrayList<PreviewResponse> previewResponseList = this.getPreviewResponseList(courses);

        return previewResponseList;
    }

    private ArrayList<PreviewResponse> getPreviewResponseList(Collection<Course> courses) {

        ArrayList<PreviewResponse> previewResponseList = new ArrayList<PreviewResponse>();
        for (Course course : courses) {

            PreviewResponse previewResponse = this.getPreviewResponse(course);
            previewResponseList.add(previewResponse);
        }

        return previewResponseList;
    }

    private PreviewResponse getPreviewResponse(Course course) {

        PreviewResponse previewResponse = new PreviewResponse();
        previewResponse.setCourseName(course.getName());
        String instructorFirstName = course.getInstructor().getStudent().getFirstName();
        String instructorLastName = course.getInstructor().getStudent().getLastName();
        previewResponse.setInstructorName(instructorFirstName + " " + instructorLastName);
        previewResponse.setRating(4.7); // hard code, fix latter.
        previewResponse.setReviewCount(53); // hard code, fix latter.
        previewResponse.setPictureURL(course.getPictureUrl());
        previewResponse.setPrice(course.getPrice());
        previewResponse.setCourseId(course.getId());
        
        return previewResponse;
    }

    public PreviewResponse getPreviewCourse(Long id) {

        Optional<Course> courseOptional = previewRepo.findById(id);

        if (!courseOptional.isPresent()) {
            throw new MyException("preview.couse.id.not.found", HttpStatus.NOT_FOUND);
        }

        Course course = courseOptional.get();
        PreviewResponse previewResponse = this.getPreviewResponse(course);

        return previewResponse;
    }

}
package com.ved.backend.service;

import java.util.ArrayList;

import com.ved.backend.model.Course;
import com.ved.backend.model.PreviewModel;
import com.ved.backend.repo.PreviewRepo;
import com.ved.backend.response.PreviewResponse;

import org.springframework.stereotype.Service;

@Service
public class PreviewServiceImpl implements PreviewService {
    
    private final PreviewRepo previewRepo;

    public PreviewResponse getPreviewCategory() {

        PreviewResponse previewResponse = new PreviewResponse();

        String[] categories = { "art", "bussiness", "academic", "design", "programming" };

        for (String category : categories) {

            ArrayList<Course> courses = previewRepo.findAll();
            ArrayList<PreviewModel> previewModelList = new ArrayList<PreviewModel>();

            for (Course course : courses) {

                String courseName = course.getName();
                String instructorFirstname = course.getInstructor().getStudent().getFirstName();
                String instructorLastname = course.getInstructor().getStudent().getLastName();
                String instructorName = instructorFirstname + " " + instructorLastname;

                PreviewModel previewModel = new PreviewModel();
                previewModel.setCourseName(courseName);
                previewModel.setInstructorName(instructorName);
                previewModel.setRating(4.4); // hard code
                previewModel.setReviewTotal(23); // hard code
                previewModel.setImageCourseURL(""); // hard code

                previewModelList.add(previewModel);
            }

            if (category.equals("art")) previewResponse.setArt(previewModelList);
            else if (category.equals("bussiness")) previewResponse.setBussiness(previewModelList);
            else if (category.equals("academic")) previewResponse.setAcademic(previewModelList);
            else if (category.equals("design")) previewResponse.setDesign(previewModelList);
            else if (category.equals("programming")) previewResponse.setProgramming(previewModelList);
            
        }

        previewResponse.setMyCourse(null);

        return previewResponse;
    }

    public PreviewResponse getPreviewMyCourse() {

        ArrayList<Course> courses = previewRepo.findAll();
        ArrayList<PreviewModel> previewModelList = new ArrayList<PreviewModel>();

        for (Course course : courses) {

            String courseName = course.getName();
            String instructorFirstname = course.getInstructor().getStudent().getFirstName();
            String instructorLastname = course.getInstructor().getStudent().getLastName();
            String instructorName = instructorFirstname + " " + instructorLastname;

            PreviewModel previewModel = new PreviewModel();
            previewModel.setCourseName(courseName);
            previewModel.setInstructorName(instructorName);
            previewModel.setRating(4.4); // hard code
            previewModel.setReviewTotal(23); // hard code
            previewModel.setImageCourseURL(""); // hard code

            previewModelList.add(previewModel);
        }

        PreviewResponse previewResponse = this.getPreviewCategory();
        previewResponse.setMyCourse(previewModelList);

        return previewResponse;
    }

    public PreviewServiceImpl(PreviewRepo previewRepo) {
        this.previewRepo = previewRepo;
    }
}
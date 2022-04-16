package com.ved.backend.service;

import java.util.Optional;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.Course;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.response.OverviewResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OverviewServiceImpl implements OverviewService {

    private final CourseRepo courseRepo;
 
    public OverviewServiceImpl(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    public OverviewResponse getOverviewCourse(Long id) {

        Optional<Course> courseOptional = courseRepo.findById(id);

        if (!courseOptional.isPresent()) {
            throw new MyException("overview.course.id.not.found", HttpStatus.NOT_FOUND);
        }

        Course course = courseOptional.get();

        OverviewResponse overviewResponse = new OverviewResponse();
        overviewResponse.setCourseId(course.getId());
        overviewResponse.setCourseName(course.getName());
        overviewResponse.setPrice(course.getPrice());

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

}
package com.ved.backend.service;

import com.ved.backend.exception.tempException.MyException;
import com.ved.backend.exception.CourseNotFoundException;
import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.response.AboutCourseResponse;
import com.ved.backend.response.VideoResponse;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepo courseRepo;
    private final CourseStateRepo courseStateRepo;
    private final CategoryRepo categoryRepo;

    // private final StudentCourseService studentCourseService;
    private final PrivateObjectStorageService privateObjectStorageService;

    private static final Logger log = LoggerFactory.getLogger(CourseService.class);



    //////////////////////////////////////////////////////////

    // public VideoResponse getVideoCourseUrl(Long courseId, int chapterIndex, int sectionIndex, String username) {
    //     StudentCourse studentCourse = studentCourseService.auth(courseId, username);
    //     String fileName = "course_vid_" + courseId + "_c" + chapterIndex + "_s" + sectionIndex + ".mp4";
    //     return VideoResponse.builder()
    //         .videoUrl(privateObjectStorageService.readFile(fileName, username))
    //         .pictureUrl(studentCourse.getCourse().getPictureUrl())
    //         .chapterName(studentCourse.getCourse().getChapters().get(chapterIndex).getName())
    //         .sectionName(studentCourse.getCourse().getChapters().get(chapterIndex).getSections().get(sectionIndex).get("name").toString())
    //         .build();
    // }


    //////////////////////////////////////////////////////////

    public Course getById(Long courseId) {
        log.info("Get course by id {}", courseId);
        return courseRepo.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    public Course getByIdAndCourseState(Long courseId, CourseState courseState) {
        log.info("Get course by id: {} and course state: {}", courseId, courseState.getName());
        return courseRepo.findByIdAndCourseState(courseId, courseState)
            .orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    public List<Course> getByCategoryAndCourseState(Category category, CourseState courseState) {
        log.info("Get course by published course state and category {}", category.getName());
        return courseRepo.findCoursesByCategoryAndCourseState(category, courseState);
    }

    public Course getByIdAndPrice(Long courseId, Long price) {
        log.info("Get course id {} and price {}", courseId, price);
        return courseRepo.findByIdAndPrice(courseId, price)
            .orElseThrow(() -> new BadRequestException("Course id " + courseId + " not free"));
    }

    /* ****************************************************************************************** */

    public AboutCourseResponse getAboutCourse(Long courseId) {

        Optional<Course> courseOptional = courseRepo.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new MyException("course.id.not.found", HttpStatus.NOT_FOUND);
        }

        Course course = courseOptional.get();
        AboutCourseResponse response = new AboutCourseResponse(course);

        return response;
    }


    public Collection<CourseStateRepo.IdAndNameOnly> getAllCourseStates() {
//        log.info("Getting all course states");
        return courseStateRepo.findAllBy();
    }

    public Collection<CategoryRepo.IdAndNameOnly> getAllCategories() {
//        log.info("Getting all categories");
        return categoryRepo.findAllBy();
    }

}
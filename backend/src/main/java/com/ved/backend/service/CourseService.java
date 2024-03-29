package com.ved.backend.service;

import com.ved.backend.exception.CourseNotFoundException;
import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.response.AboutCourseResponse;
import com.ved.backend.response.CourseResponse;
import com.ved.backend.response.OverviewResponse;
import com.ved.backend.response.VideoResponse;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CourseService {

    private final AuthService authService;
    private final PrivateObjectStorageService privateObjectStorageService;

    private final CategoryRepo categoryRepo;
    private final CourseStateRepo courseStateRepo;
    private final CourseRepo courseRepo;

    private static final Logger log = LoggerFactory.getLogger(CourseService.class);

    public CourseResponse getCourse(Long courseId, String username) {
        log.info("Get course id: {} by username: {}", courseId, username);
        StudentCourse studentCourse = authService.authorized(username, courseId);
        return new CourseResponse(studentCourse.getCourse());
    }

    public VideoResponse getVideoUrl(Long courseId, int chapterIndex, int sectionIndex, String username) {
        log.info("Get video url course id: {} by username: {}", courseId, username);
        StudentCourse studentCourse = authService.authorized(username, courseId);
        String fileName = "course_vid_" + courseId + "_c" + chapterIndex + "_s" + sectionIndex + ".mp4";
        String videoUrl = privateObjectStorageService.readFile(fileName, username);
        return VideoResponse.builder()
            .videoUrl(videoUrl)
            .pictureUrl(studentCourse.getCourse().getPictureUrl())
            .chapterName(studentCourse.getCourse().getChapters().get(chapterIndex).getName())
            .sectionName(studentCourse.getCourse().getChapters().get(chapterIndex).getSections().get(sectionIndex).get("name").toString())
            .build();
    }

    public String getHandoutUrl(Long courseId, int chapterIndex, int sectionIndex, int handoutIndex, String username) {
        log.info("Get handout url course id: {} by username: {}", courseId, username);
        StudentCourse studentCourse = authService.authorized(username, courseId);
        @SuppressWarnings("unchecked")
        List<Map<String, String>> handouts = (List<Map<String, String>>) studentCourse
            .getCourse()
            .getChapters()
            .get(chapterIndex)
            .getSections()
            .get(sectionIndex)
            .get("handouts");
        String fileName = handouts.get(handoutIndex).get("handoutUri");
        return privateObjectStorageService.readFile(fileName, username);
    }

    public AboutCourseResponse getAboutCourse(Long courseId, String username) {
        log.info("Get about course id: {} by username: {}", courseId, username);
        StudentCourse studentCourse = authService.authorized(username, courseId);
        return new AboutCourseResponse(studentCourse.getCourse());
    }

    /* ****************************************************************************************** */

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

    /* ****************************************************************************************** */

    public Collection<CourseStateRepo.IdAndNameOnly> getAllCourseStates() {
        // log.info("Getting all course states");
        return courseStateRepo.findAllBy();
    }

    public Collection<CategoryRepo.IdAndNameOnly> getAllCategories() {
        // log.info("Getting all categories");
        return categoryRepo.findAllBy();
    }

    public OverviewResponse getOverviewCourse(Long courseId, String username) {
        Course course = courseRepo
            .findById(courseId)
            .orElseThrow(() -> new CourseNotFoundException(courseId));
        String thisCourseOf;
        if (course.getInstructor().getStudent().getAppUser().getUsername().equals(username)) {
            thisCourseOf = "instructor own";
        } else {
            boolean isStudentCourse;
            try {
                authService.authorized(username, courseId);
                isStudentCourse = true;
            } catch (Exception ex) {
                isStudentCourse = false;
            }
            thisCourseOf = isStudentCourse == true ? "student own" : "student";
        }
        OverviewResponse overviewResponse = new OverviewResponse(course);
        overviewResponse.setStateOfCourse(thisCourseOf);
        return overviewResponse;
    }

}
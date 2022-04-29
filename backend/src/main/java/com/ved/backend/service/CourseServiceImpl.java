package com.ved.backend.service;

import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.response.CourseResponse;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;
    private final CourseStateRepo courseStateRepo;

    public CourseResponse getCourse(Long courseId) {
        Course course = courseRepo.getById(courseId);

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setCourseId(course.getId());
        courseResponse.setContent(course.getChapters());
        
        return courseResponse;
    }

    @Override
    public List<Map<String,Object>> getPendingCourses() {
        CourseState pendingState = courseStateRepo.findByName("PENDING");
        List<Course> pendingCourses = courseRepo.findCoursesByCourseState(pendingState);
        List<Map<String,Object>> pendingCoursesJson = new ArrayList<>();
        for(Course course: pendingCourses) {
            Map<String, Object> courseJson = new HashMap<>();
            courseJson.put("id", course.getId());
            courseJson.put("name", course.getName());
            Student student = course.getInstructor().getStudent();
            courseJson.put("instructorName", student.getFirstName() + " " + student.getLastName());
            pendingCoursesJson.add(courseJson);
        }
        return pendingCoursesJson;
    }

    @Override
    public Map<String, Object> getPendingCourse(Long courseId) {
        CourseState pendingState = courseStateRepo.findByName("PENDING");
        try {
            Course course = courseRepo.findCourseByCourseStateAndId(pendingState, courseId);
            Map<String, Object> courseJson = new HashMap<>();
            courseJson.put("name", course.getName());
            courseJson.put("price", course.getPrice());
            courseJson.put("pictureUrl", course.getPictureUrl());
            Map<String, Object> instructorInfo = new HashMap<>();
            Student student = course.getInstructor().getStudent();
            instructorInfo.put("firstName", student.getFirstName());
            instructorInfo.put("lastName", student.getLastName());
            instructorInfo.put("biography", student.getBiography());
            instructorInfo.put("occupation", student.getOccupation());
            instructorInfo.put("profilePicUri", student.getProfilePicUri());
            courseJson.put("instructorInfo" , instructorInfo);
            courseJson.put("category", course.getCategory().getName());
            courseJson.put("requirement", course.getRequirement());
            courseJson.put("overview", course.getOverview());
            courseJson.put("chapters", course.getChapters());
            return courseJson;
        } catch (Exception exception) {
            throw new RuntimeException("Course not found");
        }
    }

    public CourseServiceImpl(CourseRepo courseRepo, CourseStateRepo courseStateRepo) {
        this.courseRepo = courseRepo;
        this.courseStateRepo = courseStateRepo;
    }

}
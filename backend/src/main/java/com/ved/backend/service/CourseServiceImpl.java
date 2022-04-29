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
    public List<Map<String,Object>> getPendingCourse() {
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

    public CourseServiceImpl(CourseRepo courseRepo, CourseStateRepo courseStateRepo) {
        this.courseRepo = courseRepo;
        this.courseStateRepo = courseStateRepo;
    }

}
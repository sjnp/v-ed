package com.ved.backend.service;

import com.ved.backend.model.AppUser;
import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class StudentCourseService {

    private final StudentCourseRepo studentCourseRepo;
    private final AppUserRepo appUserRepo;
    // private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    // private final PublishedCourseRepo publishedCourseRepo;

    public void buyFreeCourse(Long courseId, String username) {

        AppUser appUser = appUserRepo.findByUsername(username);
        Student student = appUser.getStudent();

        // Optional<Course> courseOptional = courseRepo.findById(courseId);
        // Course course = courseOptional.get();
        Course course = courseRepo.findCourseById(courseId);

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourse(course);
        studentCourse.setStudent(student);

        studentCourseRepo.save(studentCourse);
    }
}
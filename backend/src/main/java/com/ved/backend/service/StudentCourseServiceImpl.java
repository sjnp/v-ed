package com.ved.backend.service;

import java.util.Objects;
import java.util.Optional;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Course;
import com.ved.backend.model.PublishedCourse;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.PublishedCourseRepo;
import com.ved.backend.repo.StudentCourseRepo;
import com.ved.backend.repo.StudentRepo;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepo studentCourseRepo;
    private final AppUserRepo appUserRepo;
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final PublishedCourseRepo publishedCourseRepo;
 
    public StudentCourseServiceImpl(
        StudentCourseRepo studentCourseRepo, 
        AppUserRepo appUserRepo,
        StudentRepo studentRepo,
        CourseRepo courseRepo,
        PublishedCourseRepo publishedCourseRepo
    ) {
        this.studentCourseRepo = studentCourseRepo;
        this.appUserRepo = appUserRepo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.publishedCourseRepo = publishedCourseRepo;
    }

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
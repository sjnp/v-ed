package com.ved.backend.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Category;
import com.ved.backend.model.Chapter;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.PublishedCourse;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.repo.InstructorRepo;
import com.ved.backend.repo.PublishedCourseRepo;
import com.ved.backend.repo.StudentCourseRepo;
import com.ved.backend.repo.StudentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockDatabase {
    
    @Autowired private AppRoleRepo appRoleRepo;
    @Autowired private AppUserRepo appUserRepo;
    @Autowired private StudentRepo studentRepo;
    @Autowired private InstructorRepo instructorRepo;
    @Autowired private CourseStateRepo courseStateRepo;
    @Autowired private CategoryRepo categoryRepo;
    @Autowired private CourseRepo courseRepo;
    @Autowired private PublishedCourseRepo publishedCourseRepo;
    @Autowired private StudentCourseRepo studentCourseRepo;

    public void clear() {
        studentCourseRepo.deleteAll();
        publishedCourseRepo.deleteAll();
        courseRepo.deleteAll();
        categoryRepo.deleteAll();
        courseStateRepo.deleteAll();
        instructorRepo.deleteAll();
        studentRepo.deleteAll();
        appUserRepo.deleteAll();
        appRoleRepo.deleteAll();
    }
    
    public void mock_app_role() {
        List<String> roles = Arrays.asList("ADMIN", "STUDENT", "INSTRUCTOR");
        for(String role : roles) {
            AppRole appRole = AppRole.builder().name(role).build();
            appRoleRepo.save(appRole);
        }
    }

    public void mock_student() {
        AppRole studentRole = appRoleRepo.findByName("STUDENT");
        Collection<AppRole> appRoles = new ArrayList<>();
        appRoles.add(studentRole);

        AppUser appUser = AppUser.builder()
            .username("student@test.com")
            .password("password")
            .appRoles(appRoles)
            .build();

        Student student = Student.builder()
            .firstName("StudentFirstname")
            .lastName("StudentLastName")
            .biography("Biography student")
            .occupation("Occupation student")
            .profilePicUri("profile_student.jpg")
            .appUser(appUser)
            .build();
        
        appUser.setStudent(student);

        appUserRepo.save(appUser);
        studentRepo.save(student);
    }

    public void mock_instructor() {
        AppRole studentRole = appRoleRepo.findByName("STUDENT");
        AppRole instructoRole = appRoleRepo.findByName("INSTRUCTOR");
        Collection<AppRole> appRoles = new ArrayList<>();
        appRoles.add(studentRole);
        appRoles.add(instructoRole);

        AppUser appUser = AppUser.builder()
            .username("instructor@test.com")
            .password("password")
            .appRoles(appRoles)
            .build();

        Student student = Student.builder()
            .firstName("InstructorFirstname")
            .lastName("InstructorLastName")
            .biography("Biography instructor")
            .occupation("Occupation instructor")
            .profilePicUri("profile_instructor.jpg")
            .appUser(appUser)
            .build();

        Instructor instructor = Instructor.builder()
            .student(student)
            .recipientId("recipient_id")
            .build();

        appUser.setStudent(student);
        student.setInstructor(instructor);

        appUserRepo.save(appUser);
        studentRepo.save(student);
        instructorRepo.save(instructor);
    }

    public void mock_course_state() {
        List<String> names = Arrays.asList("INCOMPLETE", "PENDING", "APPROVED", "REJECTED", "PUBLISHED");
        for(String name : names) {
            CourseState courseState = CourseState.builder().name(name).build();
            courseStateRepo.save(courseState);
        }
    }

    public void mock_category() {
        List<String> names = Arrays.asList("ACADEMIC", "ART", "BUSINESS", "DESIGN", "PROGRAMMING");
        for(String name : names) {
            Category category = Category.builder().name(name).build();
            categoryRepo.save(category);
        }
    }

    public void mock_course(Long price, String courseStateName, String categoryName) {
        AppUser appUser = appUserRepo.findByUsername("instructor@test.com");
        Student student = studentRepo.findById(appUser.getStudent().getId()).get();
        Instructor instructor = student.getInstructor();

        Chapter chapter = new Chapter();
        HashMap<String, String> assignment = new HashMap<>();
        assignment.put("assignment 1", "assingment 1 ?");
        assignment.put("assignment 2", "assingment 2 ?");
        assignment.put("assignment 3", "assingment 3 ?");
        List<HashMap<String, String>> assignemnts = new ArrayList<>();
        assignemnts.add(assignment);
        HashMap<String, Object> section = new HashMap<>();
        section.put("section 1", "section 1");
        section.put("section 2", "section 2");
        section.put("section 3", "section 3");
        List<HashMap<String, Object>> sections = new ArrayList<>();
        sections.add(section);
        chapter.setName("chapter 1");
        chapter.setAssignments(assignemnts);
        chapter.setSections(sections);
        List<Chapter> chapters = new ArrayList<>();
        chapters.add(chapter);

        CourseState courseState = courseStateRepo.findByName(courseStateName);

        Category category = categoryRepo.findByName(categoryName).get();

        Course course = Course.builder()
            .instructor(instructor)
            .name("course " + category.getName())
            .overview("overview course " + category.getName())
            .requirement("requirement course " + category.getName())
            .price(price)
            .chapters(chapters)
            .pictureUrl("course_picture.jpg")
            .category(category)
            .courseState(courseState)
            .build();

        PublishedCourse publishedCourse = PublishedCourse.builder()
            .course(course)
            .star(0D)
            .totalScore(0D)
            .totalUser(0L)
            .build();

        instructor.getCourses().add(course);
        course.setPublishedCourse(publishedCourse);

        courseRepo.save(course);
        instructorRepo.save(instructor);
    }

    public void mock_student_course(Student student, Course course) {
        StudentCourse studentCourse = StudentCourse.builder()
            .student(student)
            .course(course)
            .build();
        studentCourseRepo.save(studentCourse);
    }

}
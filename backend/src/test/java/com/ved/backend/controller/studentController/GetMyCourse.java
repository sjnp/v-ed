package com.ved.backend.controller.studentController;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsString;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.hasSize;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.StudentCourseRepo;
import com.ved.backend.repo.StudentRepo;
import com.ved.backend.response.CourseCardResponse;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetMyCourse {
 
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private StudentCourseRepo studentCourseRepo;

    @Test
    @Order(1)
    @WithMockUser(username = "supawet@gmail.com")
    public void givenUsername_whenMyCourseHaveData_thenReturnOkstatusAndCourseCardResponseList() throws Exception {
        // given
        Student student = appUserRepo.findByUsername("supawet@gmail.com").getStudent();
        Course course = courseRepo.findById(41L).get();
        // new StudentCourse();
        StudentCourse studentCourse = StudentCourse.builder()
            .student(student)
            .course(course)
            .build();
        studentCourseRepo.save(studentCourse);
        List<StudentCourse> studentCourses = studentCourseRepo.findByStudent(student);
        int actualSize = studentCourses.size();
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/students/courses"));
        // then
        resultActions
            .andExpect(status().isOk())
            // .andExpect(jsonPath("$").value(courseCardResponses))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(actualSize)));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "supawet@gmail.com")
    public void givenUsername_whenMyCourseNoHaveData_thenReturnOkstatusAndEmptyCourseCardResponseList() throws Exception {
        // given
        studentCourseRepo.deleteAll();
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/students/courses"));
        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(0)));
    }

}
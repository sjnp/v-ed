package com.ved.backend.integration.student.course;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.StudentCourseRepo;
import com.ved.backend.repo.StudentRepo;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetMyCourse {
 
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentCourseRepo studentCourseRepo;

    @Autowired
    private MockDatabase mockDatabase;

    @Test
    @Order(1)
    public void init() {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        mockDatabase.mock_student();
        mockDatabase.mock_instructor();
        mockDatabase.mock_category();
        mockDatabase.mock_course_state();
        mockDatabase.mock_course(0L, "PUBLISHED", "BUSINESS");
        mockDatabase.mock_course(200L, "PUBLISHED", "DESIGN");
        
        Student student = studentRepo.findAll().get(0);
        List<Course> courses = courseRepo.findAll();
        for(Course course : courses) {
            mockDatabase.mock_student_course(student, course);
        }
    }

    @Test
    @Order(2)
    @WithMockUser(username = "student@test.com")
    public void givenUsername_whenMyCourseHaveData_thenReturnOkstatusAndCourseCardResponseList() throws Exception {
        // given
        List<Course> courses = courseRepo.findAll();
        int actualSize = courses.size();
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/students/courses"));
        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(actualSize)));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "student@test.com")
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

    @Test
    @Order(4)
    public void clear() {
        mockDatabase.clear();
    }

}
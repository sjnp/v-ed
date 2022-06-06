package com.ved.backend.integration.student.course;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.ved.backend.model.AppUser;
import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.StudentCourseRepo;
import com.ved.backend.response.CourseResponse;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetCourseIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private StudentCourseRepo studentCourseRepo;

    @Autowired
    private MockDatabase mockDatabase;

    @Test
    @Order(1)
    public void init() throws Exception {
        mockDatabase.clear();
        mockDatabase.mock_app_role();

        mockDatabase.mock_register_student();

        mockDatabase.mock_instructor();
        mockDatabase.mock_category();
        mockDatabase.mock_course_state();
        mockDatabase.mock_course(1000L, "PUBLISHED", "DESIGN");
        
        AppUser appUser = appUserRepo.findAppUserByUsername("student@test.com").get();
        Student student = appUser.getStudent();
        Course course = courseRepo.findAll().get(0);
        mockDatabase.mock_student_course(student, course);
    }

    @Test
    @Order(2)
    public void givenUsernameAndCourseId_whenSuccess_thenReturnCourseResponse() throws Exception {
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");
        // given
        Course course = studentCourseRepo.findAll().get(0).getCourse();
        Long courseId = course.getId();
        CourseResponse courseResponse = new CourseResponse(course);
        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/students/courses/" + courseId)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.courseId").value(courseResponse.getCourseId()))
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    @Order(3)
    public void givenUsernameAndCourseId_whenCourseIdNotFound_thenThrowUnauthorizedException() throws Exception {
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");
        // given
        Long courseId = 0L;
        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/students/courses/" + courseId)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );
        // then
        resultActions
            .andExpect(status().isNotFound())
            .andExpect(status().reason(containsString(String.format("Course id %s not found", courseId))));
    }

    @Test
    @Order(4)
    public void clear() throws Exception {
        mockDatabase.clear();
    }

}
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class FreeCourseIT {
 
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockDatabase mockDatabase;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CourseStateRepo courseStateRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Test
    @Order(1)
    public void init() throws Exception {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        // mockDatabase.mock_student();
        mockDatabase.mock_register_student();

        mockDatabase.mock_instructor();
        mockDatabase.mock_category();
        mockDatabase.mock_course_state();
        mockDatabase.mock_course(0L, "PUBLISHED", "BUSINESS");
        mockDatabase.mock_course(200L, "PUBLISHED", "DESIGN");
    }

    @Test
    @Order(2)
    public void givenUsernameAndCourseId_whenCoursePriceNotZero_thenReturnBadRequestStatus() throws Exception {
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");
        // given
        Category category = categoryRepo.findByName("DESIGN").get();
        CourseState courseState = courseStateRepo.findByName("PUBLISHED");
        List<Course> courses = courseRepo.findCourseByCategoryAndCourseState(category, courseState);
        Long courseId = courses.get(0).getId();
        // when
        String payload = objectMapper.writeValueAsString(courseId);
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/free/course")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(payload)
        );
        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Course id " + courseId + " not free")));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "student@test.com")
    public void givenUsernameAndCourseId_whenGetFreeCourseSuccess_thenReturnCreatedStatus() throws Exception {
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");
        // given
        Category category = categoryRepo.findByName("BUSINESS").get();
        CourseState courseState = courseStateRepo.findByName("PUBLISHED");
        List<Course> courses = courseRepo.findCourseByCategoryAndCourseState(category, courseState);
        Long courseId = courses.get(0).getId();
        // when
        String payload = objectMapper.writeValueAsString(courseId);
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/free/course")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(payload)
        );
        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    @WithMockUser(username = "student@test.com")
    public void givenUsernameAndCourseId_whenHaveCourseAlready_thenReturnConflictStatus() throws Exception {
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");
        // given
        Category category = categoryRepo.findByName("BUSINESS").get();
        CourseState courseState = courseStateRepo.findByName("PUBLISHED");
        List<Course> courses = courseRepo.findCourseByCategoryAndCourseState(category, courseState);
        Long courseId = courses.get(0).getId();
        // when
        String payload = objectMapper.writeValueAsString(courseId);
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/free/course")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(payload)
        );
        // then
        resultActions
            .andExpect(status().isConflict())
            .andExpect(status().reason(containsString("You have this course already")));
    }

    @Test
    @Order(5)
    public void clear() {
        mockDatabase.clear();
    }

}
package com.ved.backend.integration.student.student_course;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class BuyFreeCourseIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockDatabase mockDatabase;

    @Autowired
    private CourseRepo courseRepo;

    @Test
    @Order(1)
    public void init() throws Exception {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        mockDatabase.mock_register_student();
        mockDatabase.mock_instructor();
        mockDatabase.mock_category();
        mockDatabase.mock_course_state();
        mockDatabase.mock_course(0L, "PUBLISHED", "BUSINESS");
        mockDatabase.mock_course(200L, "PUBLISHED", "ART");
    }

    @Test
    @Order(2)
    public void givenCourseId_whenPriceNotZero_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll()
            .stream()
            .filter(course -> course.getPrice() != 0L)
            .collect(Collectors.toList())
            .get(0)
            .getId();
        String payload = objectMapper.writeValueAsString(courseId);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/course/free")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("This course not free")));
    }

    @Test
    @Order(3)
    public void givenCourseId_whenSuccess_thenReturnCreatedStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll()
            .stream()
            .filter(course -> course.getPrice() == 0L)
            .collect(Collectors.toList())
            .get(0)
            .getId();
        String payload = objectMapper.writeValueAsString(courseId);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/course/free")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    public void givenCourseId_whenDuplicate_thenReturnConflictStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll()
            .stream()
            .filter(course -> course.getPrice() == 0L)
            .collect(Collectors.toList())
            .get(0)
            .getId();
        String payload = objectMapper.writeValueAsString(courseId);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/course/free")
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
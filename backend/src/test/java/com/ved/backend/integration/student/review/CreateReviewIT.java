package com.ved.backend.integration.student.review;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.request.ReviewRequest;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class CreateReviewIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockDatabase mockDatabase;

    @Autowired
    private AppUserRepo appUserRepo;

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
        mockDatabase.mock_course(1000L, "PUBLISHED", "PROGRAMMING");

        Student student = appUserRepo.findByUsername("student@test.com").getStudent();
        Course course = courseRepo.findAll().get(0);
        mockDatabase.mock_student_course(student, course);
    }
    
    @Test
    @Order(2)
    public void givenReviewRequest_whenSuccess_thenReturnCreatedStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        ReviewRequest reviewRequest = ReviewRequest.builder()
            .courseId(courseId)
            .rating(5.0)
            .review("Test create review success")
            .build();
        String payload = objectMapper.writeValueAsString(reviewRequest);
        
        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/review")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    public void givenCourseId_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        ReviewRequest reviewRequest = ReviewRequest.builder()
            .courseId(null)
            .rating(5.0)
            .review("Test create review success")
            .build();
        String payload = objectMapper.writeValueAsString(reviewRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/review")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Course id is required")));
    }

    @Test
    @Order(4)
    public void givenRating_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        ReviewRequest reviewRequest = ReviewRequest.builder()
            .courseId(courseId)
            .rating(null)
            .review("Test create review success")
            .build();
        String payload = objectMapper.writeValueAsString(reviewRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/review")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Rating is required")));
    }

    @Test
    @Order(5)
    public void givenReview_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        ReviewRequest reviewRequest = ReviewRequest.builder()
            .courseId(courseId)
            .rating(5.0)
            .review(null)
            .build();
        String payload = objectMapper.writeValueAsString(reviewRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/review")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Review is required")));
    }

    @Test
    @Order(6)
    public void givenReviewRequest_whenDuplicated_thenReturnConflictStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        ReviewRequest reviewRequest = ReviewRequest.builder()
            .courseId(courseId)
            .rating(5.0)
            .review("Test create review success")
            .build();
        String payload = objectMapper.writeValueAsString(reviewRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/review")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isConflict())
            .andExpect(status().reason(containsString("You have already reviewed")));
    }

    @Test
    @Order(7)
    public void clear() {
        mockDatabase.clear();
    }

}
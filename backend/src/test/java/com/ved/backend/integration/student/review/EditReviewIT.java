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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.ReviewRepo;
import com.ved.backend.request.ReviewRequest;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class EditReviewIT {

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

    @Autowired
    private ReviewRepo reviewRepo;

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
        mockDatabase.mock_create_review(course.getId(), 3.0, "OK");
    }

    @Test
    @Order(2)
    public void givenCourseId_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long reviewId = reviewRepo.findAll().get(0).getId();
        ReviewRequest reviewRequest = ReviewRequest.builder()
            .courseId(null)
            .rating(5.0)
            .review("Very Good")
            .build();
        String payload = objectMapper.writeValueAsString(reviewRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/students/courses/reviews/" + reviewId)
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
    @Order(3)
    public void givenRating_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        Long reviewId = reviewRepo.findAll().get(0).getId();
        ReviewRequest reviewRequest = ReviewRequest.builder()
            .courseId(courseId)
            .rating(null)
            .review("Very Good")
            .build();
        String payload = objectMapper.writeValueAsString(reviewRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/students/courses/reviews/" + reviewId)
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
    @Order(4)
    public void givenReview_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        Long reviewId = reviewRepo.findAll().get(0).getId();
        ReviewRequest reviewRequest = ReviewRequest.builder()
            .courseId(courseId)
            .rating(5.0)
            .review(null)
            .build();
        String payload = objectMapper.writeValueAsString(reviewRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/students/courses/reviews/" + reviewId)
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
    @Order(5)
    public void givenReviewId_whenNotFound_thenReturnNotFoundStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        Long reviewId = 0L;
        ReviewRequest reviewRequest = ReviewRequest.builder()
            .courseId(courseId)
            .rating(5.0)
            .review("Very good")
            .build();
        String payload = objectMapper.writeValueAsString(reviewRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/students/courses/reviews/" + reviewId)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isNotFound())
            .andExpect(status().reason(containsString("Review id " + reviewId + " not found")));
    }

    @Test
    @Order(6)
    public void givenReviewRequest_whenSuccess_thenReturnNoContentStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        Long reviewId = reviewRepo.findAll().get(0).getId();
        ReviewRequest reviewRequest = ReviewRequest.builder()
            .courseId(courseId)
            .rating(5.0)
            .review("Very good")
            .build();
        String payload = objectMapper.writeValueAsString(reviewRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/students/courses/reviews/" + reviewId)
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @Order(7)
    public void clear() {
        mockDatabase.clear();
    }
    
}
package com.ved.backend.integration.instructor;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.ReviewRepo;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetAllReviewsCourseIT {

    @Autowired
    private MockMvc mockMvc;

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
        mockDatabase.mock_register_instructor();
        mockDatabase.mock_category();
        mockDatabase.mock_course_state();
        mockDatabase.mock_course(1000L, "PUBLISHED", "ART");

        Student student = appUserRepo.findByUsername("student@test.com").getStudent();
        Course course = courseRepo.findAll().get(0);
        mockDatabase.mock_student_course(student, course);
    }

    @Test
    @Order(2)
    public void givenCourseId_whenNoOwner_thenReturnUnauthorizedStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_instructor();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = 0L;

        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/instructors/courses/" + courseId + "/reviews")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isUnauthorized())
            .andExpect(status().reason(containsString("You are not owner this course")));
    }

    @Test
    @Order(3)
    public void givenCourseId_whenNoHaveData_thenReturnOkStatusAndEmptyReviewCourseResponse() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_instructor();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();

        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/instructors/courses/" + courseId + "/reviews")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.reviews").isArray())
            .andExpect(jsonPath("$.reviews").isEmpty());
    }

    @Test
    @Order(4)
    public void givenCourseId_whenHaveData_thenReturnOkStatusAndReviewCourseResponse() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_instructor();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Course course = courseRepo.findAll().get(0);
        Long courseId = course.getId();
        mockDatabase.mock_create_review(courseId, 5.0, "Test mock review, Good");
        int reviewSize = reviewRepo.findAll().size();

        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/instructors/courses/" + courseId + "/reviews")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.reviews").isArray())
            .andExpect(jsonPath("$.reviews").isNotEmpty())
            .andExpect(jsonPath("$.reviews", hasSize(reviewSize)));
    }

    @Test
    @Order(5)
    public void clear() {
        mockDatabase.clear();
    }

}
package com.ved.backend.integration.student.assignment;

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
import com.ved.backend.request.AnswerRequest;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class CreateAnswerIT {
 
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
        mockDatabase.mock_course(1000L, "PUBLISHED", "BUSINESS");

        Student student = appUserRepo.findByUsername("student@test.com").getStudent();
        Course course = courseRepo.findAll().get(0);
        mockDatabase.mock_student_course(student, course);
    }

    @Test
    @Order(2)
    public void givenAnswerRequest_whenChapterIndexNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Course course = courseRepo.findAll().get(0);
        Long courseId = course.getId();
        AnswerRequest answerRequest = AnswerRequest.builder()
            .courseId(courseId)
            .chapterIndex(null)
            .noIndex(0)
            .fileName("my_answer.pdf")
            .build();
        String payload = objectMapper.writeValueAsString(answerRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/" + courseId + "/answer")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Chapter index is required")));
    }

    @Test
    @Order(3)
    public void givenAnswerRequest_whenNoIndexNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Course course = courseRepo.findAll().get(0);
        Long courseId = course.getId();
        AnswerRequest answerRequest = AnswerRequest.builder()
            .courseId(courseId)
            .chapterIndex(0)
            .noIndex(null)
            .fileName("my_answer.pdf")
            .build();
        String payload = objectMapper.writeValueAsString(answerRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/" + courseId + "/answer")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("No index is required")));
    }

    @Test
    @Order(4)
    public void givenAnswerRequest_whenFileNameNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Course course = courseRepo.findAll().get(0);
        Long courseId = course.getId();
        AnswerRequest answerRequest = AnswerRequest.builder()
            .courseId(courseId)
            .chapterIndex(0)
            .noIndex(0)
            .fileName(null)
            .build();
        String payload = objectMapper.writeValueAsString(answerRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/" + courseId + "/answer")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("File name is required")));
    }

    @Test
    @Order(5)
    public void givenAnswerRequest_whenSuccess_thenReturnCreatedStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Course course = courseRepo.findAll().get(0);
        Long courseId = course.getId();
        AnswerRequest answerRequest = AnswerRequest.builder()
            .courseId(courseId)
            .chapterIndex(0)
            .noIndex(0)
            .fileName("my_answer.pdf")
            .build();
        String payload = objectMapper.writeValueAsString(answerRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/" + courseId + "/answer")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @Order(6)
    public void clear() throws Exception {
        mockDatabase.clear();
    }

}
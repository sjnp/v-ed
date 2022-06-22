package com.ved.backend.integration.student.post;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.PostRepo;
import com.ved.backend.request.CommentRequest;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class CreateCommentIT {

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
    private PostRepo postRepo;

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
        mockDatabase.mock_comment_state();
        mockDatabase.mock_post(course.getId(), "Topic mock test", "Detail mock test");
    }

    @Test
    @Order(2)
    public void givenPostId_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        Long postId = null;
        CommentRequest commentRequest = CommentRequest.builder()
            .comment("Test comment")
            .build();
        String payload = objectMapper.writeValueAsString(commentRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/" + courseId + "/posts/" + postId + "/comment")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void givenComment_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        Long postId = postRepo.findAll().get(0).getId();
        CommentRequest commentRequest = CommentRequest.builder()
            .comment(null)
            .build();
        String payload = objectMapper.writeValueAsString(commentRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/" + courseId + "/posts/" + postId + "/comment")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Comment is required")));
    }

    @Test
    @Order(4)
    public void givenComment_whenLengthMoreThan1000_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        Long postId = postRepo.findAll().get(0).getId();
        CommentRequest commentRequest = CommentRequest.builder()
            .comment("Test comment".repeat(100))
            .build();
        String payload = objectMapper.writeValueAsString(commentRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/" + courseId + "/posts/" + postId + "/comment")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Comments are more than 1000 characters")));
    }

    @Test
    @Order(5)
    public void givenPostId_whenNotFound_thenReturnNotFoundStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        Long postId = 0L;
        CommentRequest commentRequest = CommentRequest.builder()
            .comment("Test comment")
            .build();
        String payload = objectMapper.writeValueAsString(commentRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/" + courseId + "/posts/" + postId + "/comment")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isNotFound())
            .andExpect(status().reason(containsString("Post id " + postId + " not found")));
    }

    @Test
    @Order(6)
    public void givenCommentRequest_whenSuccess_thenReturnOkStatusAndCommentResponse() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        Long postId = postRepo.findAll().get(0).getId();
        CommentRequest commentRequest = CommentRequest.builder()
            .postId(postId)
            .comment("Test comment")
            .build();
        String payload = objectMapper.writeValueAsString(commentRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/courses/" + courseId + "/posts/" + postId + "/comment")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.comment").value(commentRequest.getComment()));
    }

    @Test
    @Order(7)
    public void clear() {
        mockDatabase.clear();
    }

}
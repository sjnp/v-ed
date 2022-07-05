package com.ved.backend.integration.student.report;


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

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.Comment;
import com.ved.backend.model.Course;
import com.ved.backend.model.Post;
import com.ved.backend.model.ReasonReport;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CommentRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.PostRepo;
import com.ved.backend.repo.ReasonReportRepo;
import com.ved.backend.request.ReportRequest;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ReportCommentIT {
    
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
    private ReasonReportRepo reasonReportRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

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

        Student student = appUserRepo.findByUsername("student@test.com").getStudent();
        Course course = courseRepo.findAll().get(0);
        mockDatabase.mock_student_course(student, course);
        mockDatabase.mock_reason_report();
        mockDatabase.mock_report_state();
        mockDatabase.mock_post(course.getId(), "Mock topic", "Mock detail");
        Post post = postRepo.findAll().get(0);
        mockDatabase.mock_comment(post.getId(), "Test mock comment");
    }

    @Test
    @Order(2)
    public void givenContentId_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        List<ReasonReport> reasonReports = reasonReportRepo.findAll();
        ReportRequest reportRequest = ReportRequest.builder()
            .contentId(null)
            .reasonReportId(reasonReports.get(0).getId())
            .reportType("comment")
            .build();
        String payload = objectMapper.writeValueAsString(reportRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/report")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Content id is required")));
    }

    @Test
    @Order(3)
    public void givenReasonReportId_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Comment comment = commentRepo.findAll().get(0);
        ReportRequest reportRequest = ReportRequest.builder()
            .contentId(comment.getId())
            .reasonReportId(null)
            .reportType("comment")
            .build();
        String payload = objectMapper.writeValueAsString(reportRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/report")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Reason report id is required")));
    }

    @Test
    @Order(4)
    public void givenReportType_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Comment comment = commentRepo.findAll().get(0);
        List<ReasonReport> reasonReports = reasonReportRepo.findAll();
        ReportRequest reportRequest = ReportRequest.builder()
            .contentId(comment.getId())
            .reasonReportId(reasonReports.get(0).getId())
            .reportType(null)
            .build();
        String payload = objectMapper.writeValueAsString(reportRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/report")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Report type is required")));
    }

    @Test
    @Order(5)
    public void givenReportType_whenInvalid_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Comment comment = commentRepo.findAll().get(0);
        List<ReasonReport> reasonReports = reasonReportRepo.findAll();
        ReportRequest reportRequest = ReportRequest.builder()
            .contentId(comment.getId())
            .reasonReportId(reasonReports.get(0).getId())
            .reportType("test")
            .build();
        String payload = objectMapper.writeValueAsString(reportRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/report")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Report type invalid")));
    }

    @Test
    @Order(6)
    public void givenReportRequest_whenSuccess_thenReturnCreatedStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Comment comment = commentRepo.findAll().get(0);
        List<ReasonReport> reasonReports = reasonReportRepo.findAll();
        ReportRequest reportRequest = ReportRequest.builder()
            .contentId(comment.getId())
            .reasonReportId(reasonReports.get(0).getId())
            .reportType("comment")
            .build();
        String payload = objectMapper.writeValueAsString(reportRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/report")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @Order(7)
    public void givenReportRequest_whenDuplicated_thenReturnConflictStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Comment comment = commentRepo.findAll().get(0);
        List<ReasonReport> reasonReports = reasonReportRepo.findAll();
        ReportRequest reportRequest = ReportRequest.builder()
            .contentId(comment.getId())
            .reasonReportId(reasonReports.get(0).getId())
            .reportType("comment")
            .build();
        String payload = objectMapper.writeValueAsString(reportRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/report")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isConflict())
            .andExpect(status().reason(containsString("You report this comment already")));
    }

    @Test
    @Order(8)
    public void clear() {
        mockDatabase.clear();
    }

}
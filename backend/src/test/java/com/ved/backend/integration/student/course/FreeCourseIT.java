package com.ved.backend.integration.student.course;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.repo.StudentCourseRepo;

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
    private StudentCourseRepo studentCourseRepo;

    @Test
    @Order(1)
    @WithMockUser(username = "supawet@gmail.com")
    public void givenUsernameAndCourseId_whenCoursePriceNotZero_thenReturnBadRequestStatus() throws Exception {
        // given
        Long courseId = 41L;
        // when
        String payload = objectMapper.writeValueAsString(courseId);
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/free/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );
        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Course id " + courseId + " not free")));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "supawet@gmail.com")
    public void givenUsernameAndCourseId_whenGetFreeCourseSuccess_thenReturnCreatedStatus() throws Exception {
        // given
        Long courseId = 40L;
        // when
        String payload = objectMapper.writeValueAsString(courseId);
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/free/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );
        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    @WithMockUser(username = "supawet@gmail.com")
    public void givenUsernameAndCourseId_whenHaveCourseAlready_thenReturnConflictStatus() throws Exception {
        // given
        Long courseId = 40L;
        // when
        String payload = objectMapper.writeValueAsString(courseId);
        ResultActions resultActions = mockMvc.perform(
            post("/api/students/free/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );
        // then
        resultActions
            .andExpect(status().isConflict())
            .andExpect(status().reason(containsString("You have this course already")));
    }

    @Test
    @Order(4)
    public void clear() {
        studentCourseRepo.deleteAll();
    }

}
package com.ved.backend.integration.user;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.Student;
import com.ved.backend.repo.StudentRepo;
import com.ved.backend.request.ProfileRequest;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class UpdateProfileIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockDatabase mockDatabase;

    @Autowired
    private StudentRepo studentRepo;

    @Test
    @Order(1)
    public void init() throws Exception {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        mockDatabase.mock_register_student();
    }

    @Test
    @Order(2)
    public void givenProfileRequest_whenUpdateFirstname_thenReturnNoContentStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        String newFirstname = "NewFirstname";
        ProfileRequest profileRequest = ProfileRequest.builder()
            .firstname(newFirstname)
            .lastname(null)
            .biography(null)
            .occupation(null)
            .build();
        String payload = objectMapper.writeValueAsString(profileRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/users/profile")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isNoContent());
        Student student = studentRepo.findAll().get(0);
        assertEquals(student.getFirstName(), newFirstname);
    }

    @Test
    @Order(3)
    public void givenProfileRequest_whenUpdateLastname_thenReturnNoContentStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        String newLastname = "NewLastname";
        ProfileRequest profileRequest = ProfileRequest.builder()
            .firstname(null)
            .lastname(newLastname)
            .biography(null)
            .occupation(null)
            .build();
        String payload = objectMapper.writeValueAsString(profileRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/users/profile")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isNoContent());
        Student student = studentRepo.findAll().get(0);
        assertEquals(student.getLastName(), newLastname);
    }

    @Test
    @Order(4)
    public void givenProfileRequest_whenUpdateBiography_thenReturnNoContentStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        String newBiography = "NewBiography";
        ProfileRequest profileRequest = ProfileRequest.builder()
            .firstname(null)
            .lastname(null)
            .biography(newBiography)
            .occupation(null)
            .build();
        String payload = objectMapper.writeValueAsString(profileRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/users/profile")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isNoContent());
        Student student = studentRepo.findAll().get(0);
        assertEquals(student.getBiography(), newBiography);
    }

    @Test
    @Order(5)
    public void givenProfileRequest_whenUpdateOccupation_thenReturnNoContentStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        String newOccupation = "NewOccupation";
        ProfileRequest profileRequest = ProfileRequest.builder()
            .firstname(null)
            .lastname(null)
            .biography(null)
            .occupation(newOccupation)
            .build();
        String payload = objectMapper.writeValueAsString(profileRequest);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/users/profile")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isNoContent());
        Student student = studentRepo.findAll().get(0);
        assertEquals(student.getOccupation(), newOccupation);
    }

    @Test
    @Order(6)
    public void clear() {
        mockDatabase.clear();
    }

}
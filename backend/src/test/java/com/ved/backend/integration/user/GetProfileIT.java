package com.ved.backend.integration.user;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.ved.backend.model.Student;
import com.ved.backend.repo.StudentRepo;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetProfileIT {

    @Autowired
    private MockMvc mockMvc;

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
    public void givenUsername_whenSuccess_thenReturnOkStatusAndProfileResponse() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Student student = studentRepo.findAll().get(0);

        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/users/profile")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstname").value(student.getFirstName()))
            .andExpect(jsonPath("$.lastname").value(student.getLastName()))
            .andExpect(jsonPath("$.displayUrl").value(student.getProfilePicUri()))
            .andExpect(jsonPath("$.biography").value(student.getBiography()))
            .andExpect(jsonPath("$.occupation").value(student.getOccupation()));
    }

    @Test
    @Order(3)
    public void clear() {
        mockDatabase.clear();
    }
    
}
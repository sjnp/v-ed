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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class VerifyPasswordIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockDatabase mockDatabase;

    @Test
    @Order(1)
    public void init() throws Exception {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        mockDatabase.mock_register_student();
    }

    @Test
    @Order(2)
    public void givenPassword_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Map<String, String> request =  new HashMap<String, String>();
        request.put("password", null);
        String payload = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/users/verify-password")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Password is required")));
    }

    @Test
    @Order(3)
    public void givenPassword_whenNotMatch_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Map<String, String> request =  new HashMap<String, String>();
        request.put("password", "invalid_password");
        String payload = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/users/verify-password")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Password Invalid")));
    }

    @Test
    @Order(4)
    public void givenPassword_whenMatch_thenReturnNoContentStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Map<String, String> request =  new HashMap<String, String>();
        request.put("password", "Password123");
        String payload = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
            post("/api/users/verify-password")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @Order(5)
    public void clear() {
        mockDatabase.clear();
    }

}
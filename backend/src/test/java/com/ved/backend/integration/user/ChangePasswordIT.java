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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.AppUser;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ChangePasswordIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockDatabase mockDatabase;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    public void init() throws Exception {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        mockDatabase.mock_register_student();
    }

    @Test
    @Order(2)
    public void givenNewPassword_whenNull_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Map<String, String> request =  new HashMap<String, String>();
        request.put("newPassword", null);
        String payload = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/users/change-password")
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
    public void givenNewPassword_whenSameOldPassword_thenReturnBadRequestStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Map<String, String> request =  new HashMap<String, String>();
        request.put("newPassword", "Password123");
        String payload = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/users/change-password")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("The old password can't be used")));
    }

    @Test
    @Order(4)
    public void givenNewPassword_whenSuccess_thenReturnNoContentStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        String newPassword = "Password123456789";
        Map<String, String> request =  new HashMap<String, String>();
        request.put("newPassword", newPassword);
        String payload = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
            put("/api/users/change-password")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        );

        // then
        resultActions.andExpect(status().isNoContent());
        AppUser appUser = appUserRepo.findByUsername("student@test.com");
        boolean isMatch = passwordEncoder.matches(newPassword, appUser.getPassword());
        assertEquals(true, isMatch);
    }

    @Test
    @Order(5)
    public void clear() {
        mockDatabase.clear();
    }
    
}
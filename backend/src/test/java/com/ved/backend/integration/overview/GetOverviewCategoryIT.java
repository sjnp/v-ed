package com.ved.backend.integration.overview;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ved.backend.util.MockDatabase;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetOverviewCategoryIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockDatabase mockDatabase;

    @Test
    @Order(1)
    public void init() {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        mockDatabase.mock_student();
        mockDatabase.mock_instructor();
        mockDatabase.mock_category();
        mockDatabase.mock_course_state();
        mockDatabase.mock_course(500L, "PUBLISHED", "ART");
        mockDatabase.mock_course(400L, "PUBLISHED", "ART");
    }

    @Test
    @Order(2)
    public void givenCategoryName_whenFoundAndHaveData_thenReturnOkStatusAndList() throws Exception {
        // given
        String categoryName = "ART";
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/overviews/category/" + categoryName));
        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(notNullValue())))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Order(3)
    public void givenCategoryName_whenFoundButNoData_thenReturnOkStatusAndEmptyList() throws Exception {
        // given
        String categoryName = "ACADEMIC";
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/overviews/category/" + categoryName));
        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(notNullValue())))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Order(4)
    public void givenCategoryName_whenNotFound_thenReturnNotFoundStatus() throws Exception {
        // given
        String categoryName = "OTHER";
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/overviews/category/" + categoryName));
        // then
        resultActions
            .andExpect(status().isNotFound())
            .andExpect(status().reason(containsString("Category " + categoryName + " not found")));
    }

    @Test
    @Order(5)
    public void clear() {
        mockDatabase.clear();
    }

}
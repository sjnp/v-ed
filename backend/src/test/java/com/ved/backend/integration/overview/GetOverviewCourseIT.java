package com.ved.backend.integration.overview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
public class GetOverviewCourseIT {
 
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenCourseId_whenFound_thenReturnOkStatusAndOverviewResponse() throws Exception {
        // given
        Long courseId = 40L;
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/overviews/courses/" + courseId));
        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(notNullValue())))
            .andExpect(jsonPath("$.*", hasSize(15)))
            .andExpect(jsonPath("$.courseId").value(courseId));
    }

    @Test
    public void givenCourseId_whenNotFound_thenNotFoundStatus() throws Exception {
        // given
        Long courseId = 0L;
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/overviews/courses/" + courseId));
        // then
        resultActions
            .andExpect(status().isNotFound())
            .andExpect(status().reason(containsString("Course id " + courseId + " not found")));
    }

}
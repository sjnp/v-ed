package com.ved.backend.controller.overviewController;

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

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
public class GetVideoExampleUrlIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenCourseId_whenFound_thenReturnOkStatusAndAccessUrlVideoExample() throws Exception {
        // given
        Long courseId = 40L;
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/overviews/video-example/courses/" + courseId));
        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isString());
    }

    @Test
    public void givenCourseId_whenNotFound_thenReturnNotFoundStatus() throws Exception {
        // given
        Long courseId = 0L;
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/overviews/video-example/courses/" + courseId));
        // then
        resultActions
            .andExpect(status().isNotFound())
            .andExpect(status().reason(containsString("Course id " + courseId + " not found")));
    }

}
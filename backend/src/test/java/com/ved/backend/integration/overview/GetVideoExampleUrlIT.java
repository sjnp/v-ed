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

import java.util.List;

import com.ved.backend.model.Course;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.util.MockDatabase;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetVideoExampleUrlIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockDatabase mockDatabase;

    @Autowired
    private CourseRepo courseRepo;

    @Test
    @Order(1)
    public void init() {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        mockDatabase.mock_student();
        mockDatabase.mock_instructor();
        mockDatabase.mock_category();
        mockDatabase.mock_course_state();
        mockDatabase.mock_course(500L, "PUBLISHED", "PROGRAMMING");
    }

    @Test
    @Order(2)
    public void givenCourseId_whenFound_thenReturnOkStatusAndAccessUrlVideoExample() throws Exception {
        // given
        List<Course> courses = courseRepo.findAll();
        Long courseId = courses.get(0).getId();
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/overviews/video-example/courses/" + courseId));
        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isString());
    }

    @Test
    @Order(3)
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

    @Test
    @Order(4)
    public void clear() {
        mockDatabase.clear();
    }

}
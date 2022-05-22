package com.ved.backend.integration.overview;

import org.hamcrest.Matchers;
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

import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.util.MockDatabase;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetCourseCardIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CourseStateRepo courseStateRepo;

    @Autowired
    private CourseRepo courseRepo;

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
    }

    @Test
    @Order(2)
    public void givenCourseId_whenFound_thenReturnOkstatusAndCourseCardResponse() throws Exception {
        // given
        Category category = categoryRepo.findByName("ART").get();
        CourseState courseState = courseStateRepo.findByName("PUBLISHED");
        List<Course> courses = courseRepo.findCourseByCategoryAndCourseState(category, courseState);
        Long courseId = courses.get(0).getId();
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/overviews/courses/" + courseId + "/card"));
        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*", Matchers.hasSize(8)))
            .andExpect(jsonPath("$.courseId").value(courseId));
    }

    @Test
    @Order(3)
    public void givenCourseId_whenNotFound_thenNotFoundStatus() throws Exception {
        // given
        Long courseId = 0L;
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/overviews/courses/" + courseId + "/card"));
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
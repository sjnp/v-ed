package com.ved.backend.integration.search;

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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.PublishedCourse;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.repo.PublishedCourseRepo;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class SearchIT {
 
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockDatabase mockDatabase;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CourseStateRepo courseStateRepo;

    @Autowired
    private PublishedCourseRepo publishedCourseRepo;

    @Test
    @Order(1)
    public void init() throws Exception {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        mockDatabase.mock_register_student();
        mockDatabase.mock_instructor();
        mockDatabase.mock_category();
        mockDatabase.mock_course_state();
        mockDatabase.mock_course(200L, "PUBLISHED", "ART");
        mockDatabase.mock_course(500L, "PUBLISHED", "BUSINESS");
        mockDatabase.mock_course(900L, "PUBLISHED", "PROGRAMMING");

        CourseState courseState = courseStateRepo.findByName("PUBLISHED");
        Category category = categoryRepo.findByName("PROGRAMMING").get();
        List<Course> courses = courseRepo.findCoursesByCategoryAndCourseState(category, courseState);
        Course course = courses.get(0);
        PublishedCourse publishedCourse = publishedCourseRepo.findByCourseId(course.getId()).get();
        publishedCourse.setStar(4.7);
        publishedCourseRepo.save(publishedCourse);
    }

    @Test
    @Order(2)
    public void givenName_whenMatch_thenReturnOkStatusAndCourseCardResponseList() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given & when
        ResultActions resultActions = mockMvc.perform(
            get("/api/search?name=art")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$[0].courseName", containsString("ART")));
    }

    @Test
    @Order(3)
    public void givenNameAndCategory_whenCategoryMatch_thenReturnOkStatusAndCourseCardResponseList() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given & when
        ResultActions resultActions = mockMvc.perform(
            get("/api/search?name=course&category=art")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$[0].courseName", containsString("course")))
            .andExpect(jsonPath("$[0].category", containsString("ART")));
    }

    @Test
    @Order(4)
    public void givenNameAndMinPrice_whenMinPriceOrMore_thenReturnOkStatusAndCourseCardResponseList() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given & when
        ResultActions resultActions = mockMvc.perform(
            get("/api/search?name=course&min_price=200")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].courseName", containsString("course")))
            .andExpect(jsonPath("$[1].courseName", containsString("course")))
            .andExpect(jsonPath("$[2].courseName", containsString("course")))
            .andExpect(jsonPath("$[0].price", greaterThanOrEqualTo(200)))
            .andExpect(jsonPath("$[1].price", greaterThanOrEqualTo(200)))
            .andExpect(jsonPath("$[2].price", greaterThanOrEqualTo(200)));
    }

    @Test
    @Order(5)
    public void givenNameAndMaxPrice_whenLimitMaxPrice_thenReturnOkStatusAndCourseCardResponseList() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given & when
        ResultActions resultActions = mockMvc.perform(
            get("/api/search?name=course&max_price=600")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].courseName", containsString("course")))
            .andExpect(jsonPath("$[1].courseName", containsString("course")))
            .andExpect(jsonPath("$[0].price", lessThanOrEqualTo(600)))
            .andExpect(jsonPath("$[1].price", lessThanOrEqualTo(600)));
    }

    @Test
    @Order(6)
    public void givenNameAndMinPriceAndMaxPrice_whenBetweenMinPirceAndMaxPrice_thenReturnOkStatusAndCourseCardResponseList() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given & when
        ResultActions resultActions = mockMvc.perform(
            get("/api/search?name=course&min_price=300&max_price=800")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].courseName", containsString("course")))
            .andExpect(jsonPath("$[0].price", greaterThanOrEqualTo(300)))
            .andExpect(jsonPath("$[0].price", lessThanOrEqualTo(800)));
    }

    @Test
    @Order(7)
    public void givenNameAndRating_whenRatingOrMore_thenReturnOkStatusAndCourseCardResponseList() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given & when
        ResultActions resultActions = mockMvc.perform(
            get("/api/search?name=course&rating=4")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].courseName", containsString("course")))
            .andExpect(jsonPath("$[0].rating", greaterThanOrEqualTo(4.0)));
    }

    @Test
    @Order(8)
    public void clear() {
        mockDatabase.clear();
    }

}
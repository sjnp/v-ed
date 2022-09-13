package com.ved.backend.integration.instructor;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.ved.backend.model.Course;
import com.ved.backend.model.Post;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.PostRepo;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetAllPostsCourseIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockDatabase mockDatabase;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private PostRepo postRepo;

    @Test
    @Order(1)
    public void init() throws Exception {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        mockDatabase.mock_register_student();
        mockDatabase.mock_register_instructor();
        mockDatabase.mock_category();
        mockDatabase.mock_course_state();
        mockDatabase.mock_course(1000L, "PUBLISHED", "DESIGN");

        Student student = appUserRepo.findByUsername("student@test.com").getStudent();
        Course course = courseRepo.findAll().get(0);
        mockDatabase.mock_student_course(student, course);
    }

    @Test
    @Order(2)
    public void givenCourseId_whenNotOwner_thenReturnUnauthorizedStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_instructor();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = 0L;

        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/instructors/courses/" + courseId + "/posts")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isUnauthorized())
            .andExpect(status().reason(containsString("You are not owner this course")));
    }

    @Test
    @Order(3)
    public void givenCourseId_whenNoData_thenReturnOkStatusAndEmptyPostCardResponseList() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_instructor();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Course course = courseRepo.findAll().get(0);
        Long courseId = course.getId();

        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/instructors/courses/" + courseId + "/posts")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(4)
    public void givenCourseId_whenNoData_thenReturnOkStatusAndPostCardResponseList() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_instructor();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Course course = courseRepo.findAll().get(0);
        Long courseId = course.getId();
        mockDatabase.mock_post(courseId, "Topic mock test", "Detail mock test");
        List<Post> posts = postRepo.findAll();

        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/instructors/courses/" + courseId + "/posts")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$", hasSize(posts.size())));
    }

    @Test
    @Order(5)
    public void clear() {
        mockDatabase.clear();
    }

}
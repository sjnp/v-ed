package com.ved.backend.integration.student.assignment;

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

import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import com.ved.backend.model.AppUser;
import com.ved.backend.model.Course;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetAssignmentAnswer {
 
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockDatabase mockDatabase;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Test
    @Order(1)
    public void init() throws Exception {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        
        mockDatabase.mock_register_student();

        mockDatabase.mock_instructor();
        mockDatabase.mock_category();
        mockDatabase.mock_course_state();
        mockDatabase.mock_course(500L, "PUBLISHED", "PROGRAMMING");
        
        AppUser appUser = appUserRepo.findByUsername("student@test.com");
        Student student = appUser.getStudent();
        List<Course> courses = courseRepo.findAll();
        for(Course course : courses) {
            mockDatabase.mock_student_course(student, course);
        }

        Long courseId = courses.get(0).getId();
        int chapterIndex = 0;
        int noIndex = 0;
        String fileName = "my_answer.doc";
        mockDatabase.mock_answer(courseId, chapterIndex, noIndex, fileName);
    }

    @Test
    @Order(2)
    public void givenChapterIndex_whenSuccess_thenRetrunOkStatusAndAssignmentAnswerResponseList() throws Exception {
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");
        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        Integer chapterIndex = 0;
        // when
        String url = String.format("/api/students/courses/%d/chapter/%d/answer", courseId, chapterIndex);
        ResultActions resultActions = mockMvc.perform(
            get(url).header(HttpHeaders.AUTHORIZATION, accessToken)
        );
        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Order(4)
    public void givenChapterIndex_whenNull_thenReturnBadRequestStatus() throws Exception {
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");
        // given
        Long courseId = courseRepo.findAll().get(0).getId();
        Integer chapterIndex = null;
        // when
        String url = String.format("/api/students/courses/%d/chapter/%d/answer", courseId, chapterIndex);
        ResultActions resultActions = mockMvc.perform(
            get(url).header(HttpHeaders.AUTHORIZATION, accessToken)
        );
        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    public void clear() {
        mockDatabase.clear();
    }

}
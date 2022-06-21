package com.ved.backend.integration.student.course;

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

import static org.hamcrest.Matchers.containsString;

import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.repo.InstructorRepo;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetAboutCourseIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockDatabase mockDatabase;

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CourseStateRepo courseStateRepo;

    @Autowired
    private InstructorRepo instructorRepo;

    @Test
    @Order(1)
    public void init() throws Exception {
        mockDatabase.clear();
        mockDatabase.mock_app_role();
        mockDatabase.mock_register_student();
        mockDatabase.mock_instructor();
        mockDatabase.mock_category();
        mockDatabase.mock_course_state();
        mockDatabase.mock_course(300L, "PUBLISHED", "PROGRAMMING");

        Student student = appUserRepo.findByUsername("student@test.com").getStudent();
        Category category = categoryRepo.findByName("PROGRAMMING").get();
        CourseState courseState = courseStateRepo.findByName("PUBLISHED");
        Course course = courseRepo.findCoursesByCategoryAndCourseState(category, courseState).get(0);
        mockDatabase.mock_student_course(student, course);
    }

    @Test
    @Order(2)
    public void givenCourseId_whenNotFound_thenReturnNotFoundStatus() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Long courseId = 0L;

        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/students/courses/" + courseId + "/about")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isNotFound())
            .andExpect(status().reason(containsString("Course id " + courseId + " not found")));
    }

    @Test
    @Order(3)
    public void givenCourseId_whenSuccess_thenReturnOkStatusAndAboutCourseResponse() throws Exception {
        // login
        ResultActions logiActions = mockDatabase.mock_login_student();
        String accessToken = "Bearer " + mockDatabase.getCredential(logiActions, "access_token");

        // given
        Course course = courseRepo.findAll().get(0);
        Student studentInstructor = instructorRepo.findAll().get(0).getStudent();

        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/students/courses/" + course.getId() + "/about")
                .header(HttpHeaders.AUTHORIZATION, accessToken)
        );

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty()) 
            .andExpect(jsonPath("$.courseId").value(course.getId()))
            .andExpect(jsonPath("$.profilePictureUrl").value(studentInstructor.getProfilePicUri()))
            .andExpect(jsonPath("$.instructorName").value(studentInstructor.getFullName()))
            .andExpect(jsonPath("$.biography").value(studentInstructor.getBiography()))
            .andExpect(jsonPath("$.occupation").value(studentInstructor.getOccupation()))
            .andExpect(jsonPath("$.overview").value(course.getOverview()))
            .andExpect(jsonPath("$.requirement").value(course.getRequirement()));
    }

    @Test
    @Order(4)
    public void clear() {
        mockDatabase.clear();
    }

}
package com.ved.backend.integration.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.configuration.CategoryProperties;
import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.RoleProperties;
import com.ved.backend.repo.*;
import com.ved.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@TestPropertySource(
    locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class ChangePendingCourseStateIT {


}

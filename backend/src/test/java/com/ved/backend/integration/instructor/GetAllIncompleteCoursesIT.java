package com.ved.backend.integration.instructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.configuration.CategoryProperties;
import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.RoleProperties;
import com.ved.backend.repo.*;
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
public class GetAllIncompleteCoursesIT {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AppUserRepo appUserRepo;

  @Autowired
  private AppRoleRepo appRoleRepo;

  @Autowired
  private RoleProperties roleProperties;

  @Autowired
  private CourseStateRepo courseStateRepo;

  @Autowired
  private CourseStateProperties courseStateProperties;

  @Autowired
  private CategoryRepo categoryRepo;

  @Autowired
  private CategoryProperties categoryProperties;

  @Autowired
  private CourseRepo courseRepo;

  @Autowired
  private ObjectMapper objectMapper;

  private String accessToken;
}

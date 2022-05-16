package com.ved.backend.service.courseService;

import java.util.Arrays;
import java.util.List;

import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.service.CourseService;
import com.ved.backend.service.CourseStateService;
import com.ved.backend.service.PrivateObjectStorageService;
import com.ved.backend.util.MockData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class GetPublishCourseByCategory {

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private CourseStateRepo courseStateRepo;

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private CourseStateService courseStateService;

    @Mock
    private PrivateObjectStorageService privateObjectStorageService;

    private CourseService courseServiceTest;

    private MockData mockData;

    @BeforeEach
    public void setUp() {
        courseServiceTest = new CourseService(
            courseRepo, 
            courseStateRepo, 
            categoryRepo, 
            privateObjectStorageService, 
            courseStateService
        );
        mockData = new MockData();
    }

    @Test
    @Order(1)
    public void givenCategoryAndPublishedCourseState_whenFound_thenReturnCourseList() {
        List<Course> courses = Arrays.asList(mockData.getCourse());
        Category category = mockData.getCategory("ART");
        CourseState courseState = mockData.getCourseState("PUBLISHED");
        // given
        given(courseRepo.findCourseByCategoryAndCourseState(category, courseState)).willReturn(courses);
        // when
        List<Course> actualResult = courseServiceTest.getByCategoryAndCourseState(category, courseState);
        // then
        assertEquals(courses, actualResult);
    }

    @Test
    @Order(2)
    public void givenCategoryAndPublishedCourseState_whenNotFound_thenReturnEmptyCourseList() {
        List<Course> courses = Arrays.asList();
        Category category = mockData.getCategory("BUSINESS");
        CourseState courseState = mockData.getCourseState("PUBLISHED");
        // given
        given(courseRepo.findCourseByCategoryAndCourseState(category, courseState)).willReturn(courses);
        // when
        List<Course> actualResult = courseServiceTest.getByCategoryAndCourseState(category, courseState);
        // then
        assertEquals(courses, actualResult);
        assertNotNull(actualResult);
    }

}

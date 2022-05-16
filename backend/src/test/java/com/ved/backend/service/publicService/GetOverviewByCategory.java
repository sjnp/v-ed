package com.ved.backend.service.publicService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.exception.NotFoundException;
import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.PublishedCourse;
import com.ved.backend.model.Student;
import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.service.CategoryService;
import com.ved.backend.service.CourseService;
import com.ved.backend.service.CourseStateService;
import com.ved.backend.service.PublicService;
import com.ved.backend.util.MockData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class GetOverviewByCategory {

    @Mock
    private CategoryService categoryService;

    @Mock
    private CourseStateService courseStateService;
    
    @Mock
    private CourseService courseService;

    @Mock
    private CourseStateProperties courseStateProperties;

    private PublicService publicServiceTest;
    private MockData mockData;

    @BeforeEach
    public void setUp() {
        publicServiceTest = new PublicService(
            categoryService, 
            courseStateService, 
            courseService, 
            courseStateProperties
        );
        mockData = new MockData();
    }
 
    @Test
    @Order(1)
    public void givenCategoryName_whenHaveData_thenCourseCardResponseList() {
        String categoryName = "DESIGN";
        // category
        Category category = mockData.getCategory(categoryName);
        // course state
        CourseState courseState = mockData.getCourseState("PUBLISHED");
        // course
        Course course = mockData.getCourse();
        Student student = mockData.getStudent();
        Instructor instructor = mockData.getInstructor();
        PublishedCourse publishedCourse = mockData.getPublishedCourse();
        instructor.setStudent(student);
        course.setInstructor(instructor);
        course.setPublishedCourse(publishedCourse);
        course.setCategory(category);
        List<Course> courses = Arrays.asList(course);
        // course card response
        CourseCardResponse courseCardResponse = new CourseCardResponse(course);
        List<CourseCardResponse> courseCardResponses = Arrays.asList(courseCardResponse);
        // given
        given(categoryService.getByName(categoryName)).willReturn(category);
        given(courseStateService.getByName(courseStateProperties.getPublished())).willReturn(courseState);
        given(courseService.getByCategoryAndCourseState(category, courseState)).willReturn(courses);
        // when
        List<CourseCardResponse> actualResult = publicServiceTest.getOverviewByCategory(categoryName);
        // then
        assertEquals(courseCardResponses.size(), actualResult.size());
        assertEquals(categoryName, actualResult.get(0).getCategory());
    }

    @Test
    @Order(2)
    public void givenCategoryName_whenNoHaveData_thenEmptyCourseCardResponseList() {
        // category
        String categoryName = "ACADEMIC";
        Category category = mockData.getCategory(categoryName);
        // course state
        CourseState courseState = mockData.getCourseState("PUBLISHED");
        // course
        List<Course> courses = Arrays.asList();
        // course card response
        List<CourseCardResponse> courseCardResponses = Arrays.asList();
        // given
        given(categoryService.getByName(categoryName)).willReturn(category);
        given(courseStateService.getByName(courseStateProperties.getPublished())).willReturn(courseState);
        given(courseService.getByCategoryAndCourseState(category, courseState)).willReturn(courses);
        // when
        List<CourseCardResponse> actualResult = publicServiceTest.getOverviewByCategory(categoryName);
        // then
        assertNotNull(actualResult);
        assertEquals(0, actualResult.size());
        assertEquals(courseCardResponses, actualResult);
    }

    @Test
    @Order(3)
    public void givenCategoryName_whenNotFound_thenThrowNotFoundException() {
        String categoryName = "SPORT";
        // given
        given(categoryService.getByName(categoryName)).willThrow(NotFoundException.class);
        // when & then
        assertThatThrownBy(() -> publicServiceTest.getOverviewByCategory(categoryName))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    @Order(4)
    public void givenCourseStateName_whenNotFound_thenThrowNotFoundException() {
        String categoryName = "PROGRAMMING";
        String courseStateName = courseStateProperties.getIncomplete();
        // given
        given(courseStateService.getByName(courseStateName)).willThrow(NotFoundException.class);
        // when & then
        assertThatThrownBy(() -> publicServiceTest.getOverviewByCategory(categoryName))
            .isInstanceOf(NotFoundException.class);
    }

}
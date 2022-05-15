package com.ved.backend.service.publicService;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.exception.NotFoundException;
import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.PublishedCourse;
import com.ved.backend.model.Student;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.response.CourseCardResponse;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class GetOverviewCategory {
 
    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private CourseStateRepo courseStateRepo;

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private CourseStateProperties courseStateProperties;

    private MockData mockData;

    private PublicService publicServiceTest;

    private Course course;

    @BeforeEach
    public void setUp() {
        publicServiceTest = new PublicService(categoryRepo, courseStateRepo, courseRepo, courseStateProperties);
        
        mockData = new MockData();

        course = mockData.getCourse();
        Student student = mockData.getStudent();
        Instructor instructor = mockData.getInstructor();
        PublishedCourse publishedCourse = mockData.getPublishedCourse();
        instructor.setStudent(student);
        course.setInstructor(instructor);
        course.setPublishedCourse(publishedCourse);
    }

    @Order(1)
    @Test
    public void givenCategoryName_whenHaveData_thenReturnListCourseCardResponse() {

        // category
        String categoryName = "ART";
        Category category = mockData.getCategory(categoryName);
        Optional<Category> categoOptional = Optional.of(category);

        // course state
        String courseStateName = "PUBLISHED";
        CourseState courseState = mockData.getCourseState(courseStateName);
        Optional<CourseState> courseStateOptional = Optional.of(courseState);

        // course
        course.setCategory(category);
        course.setCourseState(courseState);
        List<Course> courses = Arrays.asList(course);
        Optional<List<Course>> coursesOptional = Optional.of(courses);

        // course card response
        CourseCardResponse courseCardResponse = new CourseCardResponse(course);
        List<CourseCardResponse> courseCardResponses = Arrays.asList(courseCardResponse);

        // given
        given(categoryRepo.findByName(categoryName)).willReturn(categoOptional);
        given(courseStateRepo.findCourseStateByName(courseStateProperties.getPublished())).willReturn(courseStateOptional);
        given(courseRepo.findCourseByCategoryAndCourseState(category, courseState)).willReturn(coursesOptional);

        // when
        List<CourseCardResponse> actualResult = publicServiceTest.getOverviewCategory(categoryName);

        // then
        assertEquals(categoryName, actualResult.get(0).getCategory());
        assertEquals(courseCardResponses.size(), actualResult.size());
    }

    @Order(2)
    @Test
    public void givenCategoryName_whenNoHaveData_thenReturnEmptyListCourseCardResponse() {

        // category
        String categoryName = "ART";
        Category category = mockData.getCategory(categoryName);
        Optional<Category> categoOptional = Optional.of(category);

        // course state
        String courseStateName = "PUBLISHED";
        CourseState courseState = mockData.getCourseState(courseStateName);
        Optional<CourseState> courseStateOptional = Optional.of(courseState);

        // course
        List<Course> courses = Arrays.asList();
        Optional<List<Course>> coursesOptional = Optional.of(courses);

        // given
        given(categoryRepo.findByName(categoryName)).willReturn(categoOptional);
        given(courseStateRepo.findCourseStateByName(courseStateProperties.getPublished())).willReturn(courseStateOptional);
        given(courseRepo.findCourseByCategoryAndCourseState(category, courseState)).willReturn(coursesOptional);

        // when
        List<CourseCardResponse> actualResult = publicServiceTest.getOverviewCategory(categoryName);

        // then
        assertNotNull(actualResult);
        assertEquals(0, actualResult.size());
    }

    @Order(3)
    @Test
    public void givenCategoryName_whenNotFoundCategory_thenThrowNotFoundException() {

        // category
        String categoryName = "OTHER";

        // given
        given(categoryRepo.findByName(categoryName)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> publicServiceTest.getOverviewCategory(categoryName))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(String.format("Category %s not found.", categoryName));
    }

    @Order(4)
    @Test
    public void givenCourseState_whenNotEqualPublished_thenThrowNotFoundException() {

        // category
        String categoryName = "DESIGN";
        Category category = mockData.getCategory(categoryName);
        Optional<Category> categoOptional = Optional.of(category);

        // given
        given(categoryRepo.findByName(categoryName)).willReturn(categoOptional);
        given(courseStateRepo.findCourseStateByName(courseStateProperties.getPublished())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> publicServiceTest.getOverviewCategory(categoryName))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("Course state PUBLISHED not found.");
    }

    @Order(5)
    @Test
    public void givenCategoryAndCourseState_whenNotFound_thenthenThrowNotFoundException() {

        // category
        String categoryName = "PROGRAMMING";
        Category category = mockData.getCategory(categoryName);
        Optional<Category> categoOptional = Optional.of(category);

        // course state
        String courseStateName = "PUBLISHED";
        CourseState courseState = mockData.getCourseState(courseStateName);
        Optional<CourseState> courseStateOptional = Optional.of(courseState);

        // given
        given(categoryRepo.findByName(categoryName)).willReturn(categoOptional);
        given(courseStateRepo.findCourseStateByName(courseStateProperties.getPublished())).willReturn(courseStateOptional);
        given(courseRepo.findCourseByCategoryAndCourseState(category, courseState)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> publicServiceTest.getOverviewCategory(categoryName))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(String.format("Course category %s not found.", categoryName));
    }

}
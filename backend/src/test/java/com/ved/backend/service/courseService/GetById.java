package com.ved.backend.service.courseService;

import com.ved.backend.exception.baseException.NotFoundException;
import com.ved.backend.model.Course;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.service.CourseService;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class GetById {
    
    @Mock
    private CourseRepo courseRepo;

    @Mock
    private CourseStateRepo courseStateRepo;

    @Mock
    private CategoryRepo categoryRepo;

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
            privateObjectStorageService
        );
        mockData = new MockData();
    }

    @Test
    @Order(1)
    public void givenCourseId_whenFound_thenReturnCourse() {
        Long courseId = 70L;
        Course course = mockData.getCourse();
        // given
        given(courseRepo.findById(courseId)).willReturn(Optional.of(course));
        // when
        Course actualResult = courseServiceTest.getById(courseId);
        // then
        assertEquals(course, actualResult);
        assertEquals(course.getId(), actualResult.getId());
        assertEquals(course.getName(), actualResult.getName());
        assertEquals(course.getPrice(), actualResult.getPrice());
    }

    @Test
    @Order(2)
    public void givenCourseId_whenNotFound_thenThrowNotFoundException() {
        Long courseId = 0L;
        // given
        given(courseRepo.findById(courseId)).willReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> courseServiceTest.getById(courseId))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(String.format("Course id %s not found", courseId));
    }

}
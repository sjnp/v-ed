package com.ved.backend.service.courseStateService;

import com.ved.backend.model.CourseState;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.service.CourseStateService;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class GetAll {

    @Mock
    private CourseStateRepo courseStateRepo;

    private CourseStateService courseStateServiceTest;

    private MockData mockData;

    @BeforeEach
    public void setUp() {
        courseStateServiceTest = new CourseStateService(courseStateRepo);
        mockData = new MockData();
    }
    
    @Test
    @Order(1)
    public void given_whenFindAllFound_thenReturnCourseStateList() {
        List<CourseState> courseStates = Arrays.asList("incomplete", "pending", "approved", "rejected", "published")
            .stream()
            .map((name) -> mockData.getCourseState(name))
            .collect(Collectors.toList());
        // given
        given(courseStateRepo.findAll()).willReturn(courseStates);
        // when
        List<CourseState> actualResult = courseStateServiceTest.getAll();
        // then
        assertEquals(courseStates, actualResult);
    }

    @Test
    @Order(2)
    public void given_whenFindAllNotFound_thenReturnEmptyCourseStateList() {
        List<CourseState> courseStates = Arrays.asList();
        // given
        given(courseStateRepo.findAll()).willReturn(courseStates);
        // when
        List<CourseState> actualResult = courseStateServiceTest.getAll();
        // then
        assertEquals(courseStates, actualResult);
        assertNotNull(actualResult);
    }

}
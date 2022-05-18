package com.ved.backend.service.courseStateService;

import com.ved.backend.exception.CourseStateNotFoundException;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class GetByName {
    
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
    public void givenCourseStateName_whenFindByNameFound_thenReturnCourseState() {
        String courseStateName = "PUBLISHED";
        CourseState courseState = mockData.getCourseState(courseStateName);
        // given
        given(courseStateRepo.findCourseStateByName(courseStateName)).willReturn(Optional.of(courseState));
        // when
        CourseState actualResult = courseStateServiceTest.getByName(courseStateName);
        // then
        assertEquals(courseState, actualResult);
    }

    @Test
    @Order(2)
    public void givenCourseStateName_whenFindByNameNotFound_thenThrowCourseStateNotFoundException() {
        String courseStateName = "FAIL";
        // given
        given(courseStateRepo.findCourseStateByName(courseStateName)).willReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> courseStateServiceTest.getByName(courseStateName))
            .isInstanceOf(CourseStateNotFoundException.class)
            .hasMessageContaining(String.format("Course state %s not found", courseStateName));
    }

}
package com.ved.backend.service.studentCourseService;

import com.ved.backend.exception.baseException.UnauthorizedException;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.StudentCourseRepo;
import com.ved.backend.service.StudentCourseService;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class GetByStudentAndCourseId {
 
    @Mock
    private StudentCourseRepo studentCourseRepo;

    private StudentCourseService studentCourseServiceTest;
    private MockData mockData;

    @BeforeEach
    public void setUp() {
        studentCourseServiceTest = new StudentCourseService(studentCourseRepo);
        mockData = new MockData();
    }

    @Test
    @Order(1)
    public void givenStudentAndCourseId_whenFound_thenReturnStudentCourse() {
        Student student = mockData.getStudent();
        Long courseId = 1L;
        StudentCourse studentCourse = mockData.getStudentCourse();
        Optional<StudentCourse> studentCourseOptional = Optional.of(studentCourse);
        // given
        given(studentCourseRepo.findByStudentAndCourseId(student, courseId))
            .willReturn(studentCourseOptional);
        // when
        StudentCourse actual = studentCourseServiceTest.getByStudentAndCourseId(student, courseId);
        // then
        assertEquals(studentCourse, actual);
    }

    @Test
    @Order(2)
    public void givenStudentAndCourseId_whenNotFound_thenThrowUnauthorizedException() {
        Student student = mockData.getStudent();
        Long courseId = 0L;
        // given
        given(studentCourseRepo.findByStudentAndCourseId(student, courseId)).willReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> studentCourseServiceTest.getByStudentAndCourseId(student, courseId))
            .isInstanceOf(UnauthorizedException.class)
            .hasMessageContaining("You have not authorized this course");
    }

}
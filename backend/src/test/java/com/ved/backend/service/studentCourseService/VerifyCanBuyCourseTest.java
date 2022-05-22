package com.ved.backend.service.studentCourseService;

import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.model.Course;
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
public class VerifyCanBuyCourseTest {
 
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
    public void givenStudentAndCourse_whenFound_thenSuccess() {
        Course course = mockData.getCourse();
        Student student = mockData.getStudent();
        // given
        given(studentCourseRepo.findByStudentAndCourse(student, course)).willReturn(Optional.empty());
        // when
        Boolean actual = true;
        studentCourseServiceTest.verifyCanBuyCourse(student, course);
        // then
        assertEquals(true, actual);
    }

    @Test
    @Order(2)
    public void givenStudentAndCourse_whenNotFound_thenReturnFalse() {
        Course course = mockData.getCourse();
        Student student = mockData.getStudent();
        StudentCourse studentCourse = mockData.getStudentCourse();
        Optional<StudentCourse> studentCourseOptional = Optional.of(studentCourse);
        // given
        given(studentCourseRepo.findByStudentAndCourse(student, course)).willReturn(studentCourseOptional);
        // when & then
        assertThatThrownBy(() -> studentCourseServiceTest.verifyCanBuyCourse(student, course))
            .isInstanceOf(ConflictException.class)
            .hasMessageContaining("You have this course already");
    }

}
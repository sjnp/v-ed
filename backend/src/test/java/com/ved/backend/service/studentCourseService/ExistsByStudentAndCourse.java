// package com.ved.backend.service.studentCourseService;

// import com.ved.backend.model.Course;
// import com.ved.backend.model.Student;
// import com.ved.backend.repo.StudentCourseRepo;
// import com.ved.backend.service.StudentCourseService;
// import com.ved.backend.util.MockData;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import static org.mockito.BDDMockito.given;

// @ExtendWith(MockitoExtension.class)
// @TestMethodOrder(OrderAnnotation.class)
// public class ExistsByStudentAndCourse {
 
//     @Mock
//     private StudentCourseRepo studentCourseRepo;

//     private StudentCourseService studentCourseServiceTest;
//     private MockData mockData;

//     @BeforeEach
//     public void setUp() {
//         studentCourseServiceTest = new StudentCourseService(studentCourseRepo);
//         mockData = new MockData();
//     }

//     @Test
//     @Order(1)
//     public void givenStudentAndCourse_whenFound_thenSuccess() {
//         Course course = mockData.getCourse();
//         Student student = mockData.getStudent();
//         Boolean expected = true;
//         // given
//         given(studentCourseRepo.existsByStudentAndCourse(student, course)).willReturn(expected);
//         // when
//         Boolean actual = studentCourseServiceTest.existsByStudentAndCourse(student, course);
//         // then
//         assertEquals(expected, actual);
//     }

//     @Test
//     @Order(2)
//     public void givenStudentAndCourse_whenNotFound_thenReturnFalse() {
//         Course course = mockData.getCourse();
//         Student student = mockData.getStudent();
//         Boolean expected = false;
//         // given
//         given(studentCourseRepo.existsByStudentAndCourse(student, course)).willReturn(expected);
//          // when
//          Boolean actual = studentCourseServiceTest.existsByStudentAndCourse(student, course);
//          // then
//          assertEquals(expected, actual);
//     }

// }
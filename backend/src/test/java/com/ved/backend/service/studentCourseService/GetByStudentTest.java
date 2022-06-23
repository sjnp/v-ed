// package com.ved.backend.service.studentCourseService;

// import com.ved.backend.model.Student;
// import com.ved.backend.model.StudentCourse;
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

// import java.util.Arrays;
// import java.util.List;

// @ExtendWith(MockitoExtension.class)
// @TestMethodOrder(OrderAnnotation.class)
// public class GetByStudentTest {
    
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
//     public void givenStudent_whenMyCourseHaveData_thenReturnStudentCourseList() {
//         Student student = mockData.getStudent();
//         StudentCourse studentCourse = mockData.getStudentCourse();
//         List<StudentCourse> studentCourses = Arrays.asList(studentCourse);
//         // given
//         given(studentCourseRepo.findByStudent(student)).willReturn(studentCourses);
//         // when
//         List<StudentCourse> actual = studentCourseServiceTest.getByStudent(student);
//         // then
//         assertEquals(studentCourses, actual);
//     }

//     @Test
//     @Order(2)
//     public void givenUsername_whenMyCourseNoHaveData_thenReturnEmptyStudentCourseList() {
//         Student student = mockData.getStudent();
//         List<StudentCourse> emptyStudentCourses = Arrays.asList();
//         // given
//         given(studentCourseRepo.findByStudent(student)).willReturn(emptyStudentCourses);
//         // when
//         List<StudentCourse> actual = studentCourseServiceTest.getByStudent(student);
//         // then
//         assertEquals(emptyStudentCourses, actual);
//     }

// }
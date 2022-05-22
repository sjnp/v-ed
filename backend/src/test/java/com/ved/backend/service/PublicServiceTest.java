package com.ved.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jayway.jsonpath.Option;
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

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
// import static org.mockito.BDDMockito.willReturn;

@ExtendWith(MockitoExtension.class)
public class PublicServiceTest {
 
    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private CourseStateRepo courseStateRepo;

    @Mock
    private CourseRepo courseRepo;

    private PublicService publicServiceTest;

    // private Optional<Category> categoryAcademicOptional;
    // private Category categoryArt;
    // private Category categoryBusiness;
    // private Category categoryDesign;
    // private Category categoryProgramming;

    private CourseState courseStatePublished;
    // private Optional<CourseState> courseStateOptional;

    // private Optional<List<Course>> courseListOptional;

    // private CourseCardResponse courseCardResponse;

    // @BeforeAll
    // public void setUpBeforeAllTest() {

    //     // category
        

        // Category categoryAcademic = new Category();
        // categoryAcademic.setId(2L);
        // categoryAcademic.setName("ACADEMIC");
        // categoryAcademicOptional = Optional.of();

    //     categoryBusiness = new Category();
    //     categoryBusiness.setId(3L);
    //     categoryBusiness.setName("BUSINESS");

    //     categoryDesign = new Category();
    //     categoryDesign.setId(4L);
    //     categoryDesign.setName("DESIGN");

    //     categoryProgramming = new Category();
    //     categoryProgramming.setId(5L);
    //     categoryProgramming.setName("PROGRAMMING");

    //     // course state
    //     courseStatePublished = new CourseState();
    //     courseStatePublished.setId(5L);
    //     courseStatePublished.setName("PUBLISHED");



    // }

    @BeforeEach
    public void setUpBeforeEachTest() {
//        publicServiceTest = new PublicService(categoryRepo, courseStateRepo, courseRepo);

        

        // courseCardResponse = new CourseCardResponse()
    }

    @Test
    public void givenCategoryName_whenCategoryNameFound_thenReturnCategory() {

        
        // category
        Category categoryAcademic = new Category();
        categoryAcademic.setId(1L);
        categoryAcademic.setName("ACADEMIC");
        categoryAcademic.setCourses(null);
        Optional<Category> categoryAcademicOptional = Optional.of(categoryAcademic);

        // course state
        courseStatePublished = new CourseState();
        courseStatePublished.setId(5L);
        courseStatePublished.setName("PUBLISHED");
        Optional<CourseState>courseStateOptional = Optional.of(courseStatePublished);

        // course
        Course course = new Course();
        course.setId(1L);
        course.setName("Course name academic");
        course.setCategory(categoryAcademic);
        course.setCourseState(courseStatePublished);
        Student student = new Student();
        student.setFirstName("FirstName");
        student.setLastName("LastName");
        Instructor instructor = new Instructor();
        instructor.setStudent(student);
        course.setInstructor(instructor);
        PublishedCourse publishedCourse = new PublishedCourse();
        publishedCourse.setStar(5D);
        publishedCourse.setTotalUser(1L);
        course.setPublishedCourse(publishedCourse);
        course.setPrice(500L);


        List<Course> courses = new ArrayList<Course>();
        courses.add(course);
        Optional<List<Course>>courseListOptional = Optional.of(courses);
        
        


        String categoryName = "ACADEMIC";
        String courseStateName = "PUBLISHED";

        CourseCardResponse courseCardResponse = new CourseCardResponse();
        courseCardResponse.setCourseId(1L);
        courseCardResponse.setCourseName("Course academic name");
        courseCardResponse.setInstructorName("Firstname Lastname");
        courseCardResponse.setRating(5);
        courseCardResponse.setReviewCount(1L);
        courseCardResponse.setPictureURL("picture.url");
        courseCardResponse.setPrice(500);
        courseCardResponse.setCategory(categoryName);

        List<CourseCardResponse> courseCardResponses = new ArrayList<>();
        courseCardResponses.add(courseCardResponse);

        // Optional<List<CourseCardResponse>> courseCardResponseOptional = Optional.of(courseCardResponses);

        // given
        given(categoryRepo.findByName(categoryName)).willReturn(categoryAcademicOptional);
        given(courseStateRepo.findCourseStateByName(courseStateName)).willReturn(courseStateOptional);
        given(courseRepo.findCourseByCategoryAndCourseState(categoryAcademicOptional.get(), courseStateOptional.get())).willReturn(courseListOptional);
        // given(publicServiceTest.getOverviewCategory(categoryName)).willReturn()
        given(courses.stream().map((course2) -> new CourseCardResponse(course2)).collect(Collectors.toList())).willReturn(courseCardResponses);
        // when
        List<CourseCardResponse> result = publicServiceTest.getOverviewCategory(categoryName);

        // then
        assertEquals(categoryName, result.get(0).getCategory());
    }

    @Test
    public void givenCategoryName_whenCategoryNameNotFound_thenThrowNotFoundException() {

    }

    // @Test
    // public void givenCourseStateName_whenCourseStateFound_thenReturnCourseState() {

    // }

    // @Test
    // public void givenCourseStateName_whenCourseStateNotFound_thenThrowNotFoundException() {

    // }

    @Test
    public void givenCategortAndCourseState_whenCourseFound_thenReturnCourseList() {

    }

    @Test
    public void givenCategortAndCourseState_whenCourseNotFound_thenReturnEmptyCourseList() {

    }

}
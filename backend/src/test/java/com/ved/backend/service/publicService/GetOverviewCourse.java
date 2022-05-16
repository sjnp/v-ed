package com.ved.backend.service.publicService;

import java.util.List;
import java.util.Optional;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.model.Chapter;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.PublishedCourse;
import com.ved.backend.model.Student;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.response.OverviewResponse;
import com.ved.backend.service.PublicService;
import com.ved.backend.util.MockData;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class GetOverviewCourse {

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

        // course = mockData.getCourse();
        // Student student = mockData.getStudent();
        // Instructor instructor = mockData.getInstructor();
        // PublishedCourse publishedCourse = mockData.getPublishedCourse();
        // instructor.setStudent(student);
        // course.setInstructor(instructor);
        // course.setPublishedCourse(publishedCourse);


        // this.courseId = course.getId();
        // this.courseName = course.getName();
        // this.price = course.getPrice();
        // this.pictureURL = course.getPictureUrl();
        // this.overview = course.getOverview();
        // this.requirement = course.getRequirement();
        course = mockData.getCourse();

        // this.chapterList = course.getChapters();
        // List<Chapter> chapters = Arrays.asList();
        // course.setChapters();

        // this.ratingCourse = course.getPublishedCourse().getStar();
        // this.totalReview = course.getPublishedCourse().getTotalUser();
        // this.reviewList = course.getPublishedCourse().getReviews();
        PublishedCourse publishedCourse = mockData.getPublishedCourse();
        course.setPublishedCourse(publishedCourse);

        // this.instructorFirstname = course.getInstructor().getStudent().getFirstName();
        // this.instructorLastname = course.getInstructor().getStudent().getLastName();
        // this.instructorPictureURI = course.getInstructor().getStudent().getProfilePicUri();
        // this.biography = course.getInstructor().getStudent().getBiography();
        // this.occupation = course.getInstructor().getStudent().getOccupation();
        Student student = mockData.getStudent();
        Instructor instructor = mockData.getInstructor();
        instructor.setStudent(student);
        course.setInstructor(instructor);
    }

    @Order(1)
    @Test
    public void givenCourseId_whenFoundCourse_thenReturnOverviewResponse() {

        Long courseId = course.getId();
     
        // course state
        String courseStateName = "PUBLISHED";
        CourseState courseState = mockData.getCourseState(courseStateName);
        Optional<CourseState> courseStateOptional = Optional.of(courseState);

        // course
        Optional<Course> courseOptional = Optional.of(course);

        // overview response
        OverviewResponse overviewResponse = new OverviewResponse(course);

        // given
        // given(courseStateRepo.findCourseStateByName(courseStateProperties.getPublished())).willReturn(courseStateOptional);
        // given(courseRepo.findCourseByIdAndCourseState(courseId, courseState)).willReturn(courseOptional);

        // OverviewResponse actualResult = publicServiceTest.getOverviewCourse(courseId);

        // assertEquals(overviewResponse, actualResult);
    }

    @Order(2)
    @Test
    public void givenCourseId_whenNotFoundCourse_thenThrowNotFoundException() {
    
    }

    @Order(3)
    @Test
    public void givenCourseState_whenNotCourseStatePublished_thenThrowNotFoundException() {
    
    }
    
}
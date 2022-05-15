package com.ved.backend.util;

import java.time.LocalDateTime;

import com.ved.backend.model.Answer;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Category;
import com.ved.backend.model.Chapter;
import com.ved.backend.model.Comment;
import com.ved.backend.model.CommentReport;
import com.ved.backend.model.CommentState;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Post;
import com.ved.backend.model.PostReport;
import com.ved.backend.model.PublishedCourse;
import com.ved.backend.model.ReportState;
import com.ved.backend.model.Review;
import com.ved.backend.model.ReviewReport;
import com.ved.backend.model.Student;
import com.ved.backend.model.StudentCourse;

public class MockData {
    
    public Answer getAnswer() {
        Answer answer = new Answer();
        answer.setId(100L);
        answer.setChapterIndex(0);
        answer.setSectionIndex(0);
        answer.setFileName("answer_cid_0_c0_s0.txt");
        answer.setDatetime(LocalDateTime.now());
        answer.setCommentInstructor("comment from instructor test");
        answer.setStudentCourse(null);
        return answer;
    }

    public AppRole getAppRole() {
        AppRole appRole = new AppRole();
        appRole.setId(1L);
        appRole.setName("STUDENT");
        return appRole;
    }

    public AppUser getAppUser() {
        AppUser appUser = new AppUser();
        appUser.setId(20L);
        appUser.setUsername("username@test.com");
        appUser.setPassword("password");
        appUser.setAppRoles(null);
        appUser.setStudent(null);
        return appUser;
    }

    public Category getCategory(String categoryName) {
        
        Category category = new Category();
        category.setCourses(null);

        categoryName = categoryName.toUpperCase();

        switch (categoryName) {
            case "ACADEMIC":
                category.setId(1L);
                category.setName("ACADEMIC");
                break;
            case "ART":
                category.setId(2L);
                category.setName("ART");
                break;
            case "BUSINESS":
                category.setId(3L);
                category.setName("BUSINESS");
                break;
            case "DESIGN":
                category.setId(4L);
                category.setName("DESIGN");
                break;
            case "PROGRAMMING":
                category.setId(5L);
                category.setName("PROGRAMMING");
                break;
            default:
                category.setId(0L);
                category.setName("OTHER");
                break;
        }
        
        return category;
    }

    public Chapter getChapter() {
        Chapter chapter = new Chapter();
        chapter.setName("Chapter name");
        chapter.setAssignments(null);
        chapter.setSections(null);
        return chapter;
    }

    public Comment getComment() {
        Comment comment = new Comment();
        comment.setId(40L);
        comment.setComment("comment test");
        comment.setPost(null);
        comment.setCommentDateTime(LocalDateTime.now());
        comment.setCommentState(null);
        comment.setVisible(true);
        comment.setCommentReports(null);
        comment.setStudent(null);
        return comment;
    }

    public CommentReport getCommentReport() {
        CommentReport commentReport = new CommentReport();
        commentReport.setId(50L);
        commentReport.setDescription("Description comment report test");
        commentReport.setComment(null);
        commentReport.setReportState(null);
        return commentReport;
    }

    public CommentState getCommentState() {
        CommentState commentState = new CommentState();
        commentState.setId(60L);
        commentState.setName("Comment state test");
        commentState.setComments(null);
        return commentState;
    }

    public Course getCourse() {
        Course course = new Course();
        course.setId(70L);
        course.setName("Course name test");
        course.setOverview("Overview course test");
        course.setRequirement("Requirement course test");
        course.setPrice(500L);
        course.setPictureUrl("picture_sid_70.jpg");
        course.setChapters(null);
        course.setCourseState(null);
        course.setInstructor(null);
        course.setCategory(null);
        course.setPublishedCourse(null);
        course.setStudentCourses(null);
        course.setPosts(null);
        return course;
    }

    public CourseState getCourseState(String courseStateName) {
        CourseState courseState = new CourseState();        
        courseState.setCourses(null);

        courseStateName = courseStateName.toUpperCase();

        switch (courseStateName) {
            case "INCOMPLETE":
                courseState.setId(1L);
                courseState.setName("INCOMPLETE");
                break;
            case "PENDING":
                courseState.setId(2L);
                courseState.setName("PENDING");
                break;
            case "APPROVED":
                courseState.setId(3L);
                courseState.setName("APPROVED");
                break;
            case "REJECTED":
                courseState.setId(4L);
                courseState.setName("REJECTED");
                break;
            case "PUBLISHED":
                courseState.setId(5L);
                courseState.setName("PUBLISHED");
                break;
            default:
                courseState.setId(0L);
                courseState.setName("OTHER");
                break;
        }
        
        return courseState;
    }

    public Instructor getInstructor() {
        Instructor instructor = new Instructor();
        instructor.setId(90L);
        instructor.setRecipientId("Recipient id test");
        instructor.setStudent(null);
        instructor.setCourses(null);
        return instructor;
    }

    public Post getPost() {
        Post post = new Post();
        post.setId(110L);
        post.setTopic("Topic test");
        post.setDetail("Detail test");
        post.setCreateDateTime(LocalDateTime.now());
        post.setVisible(true);
        post.setComments(null);
        post.setCourse(null);
        post.setPostReports(null);
        post.setStudentCourse(null);
        return post;
    }

    public PostReport getPostReport() {
        PostReport postReport = new PostReport();
        postReport.setId(120L);
        postReport.setDescription("Description post report test");
        postReport.setPost(null);
        postReport.setReportState(null);
        return postReport;
    }

    public PublishedCourse getPublishedCourse() {
        PublishedCourse publishedCourse = new PublishedCourse();
        publishedCourse.setId(130L);
        publishedCourse.setStar(5D);
        publishedCourse.setTotalScore(100D);
        publishedCourse.setTotalUser(20L);
        publishedCourse.setReviews(null);
        publishedCourse.setCourse(null);
        return publishedCourse;
    }

    public ReportState getReportState() {
        ReportState reportState = new ReportState();
        reportState.setId(140L);
        reportState.setName("Report state test");
        reportState.setPostReports(null);
        reportState.setReviewReports(null);
        reportState.setCommentReports(null);
        return reportState;
    }

    public Review getReview() {
        Review review = new Review();
        review.setId(150L);
        review.setRating(5D);
        review.setComment("Comment review test");
        review.setVisible(true);
        review.setReviewDateTime(LocalDateTime.now());
        review.setStudent(null);
        review.setPublishedCourse(null);
        review.setReviewReports(null);
        return review;
    }

    public ReviewReport getReviewReport() {
        ReviewReport reviewReport = new ReviewReport();
        reviewReport.setId(160L);
        reviewReport.setDescription("Description review report state");
        reviewReport.setReview(null);
        reviewReport.setReportState(null);
        return reviewReport;
    }

    public Student getStudent() {
        Student student = new Student();
        student.setId(170L);
        student.setFirstName("FirstnameTest");
        student.setLastName("LarstnameTest");
        student.setProfilePicUri("display_sid_170.jpg");
        student.setBiography("Biography test");
        student.setOccupation("Occupation test");
        student.setAppUser(null);
        student.setStudentCourses(null);
        student.setComments(null);
        student.setInstructor(null);
        student.setReview(null);
        return student;
    }

    public StudentCourse getStudentCourse() {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId(180L);
        studentCourse.setChargeId("Charge id test");
        studentCourse.setTransferId("Transfer id test");
        studentCourse.setStudent(null);
        studentCourse.setAnswers(null);
        studentCourse.setCourse(null);
        studentCourse.setPosts(null);
        return studentCourse;
    }

}
package com.ved.backend.service;

import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.exception.tempException.MyException;
import com.ved.backend.model.*;
// import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.PublishedCourseRepo;
import com.ved.backend.repo.ReviewRepo;
import com.ved.backend.request.ReviewRequest;
import com.ved.backend.response.ReviewCourseResponse;
import com.ved.backend.response.ReviewResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class ReviewService {

    private final AuthService authService;

    private final ReviewRepo reviewRepo;

    // private final AppUserRepo appUserRepo;
    private final PublishedCourseRepo publishedCourseRepo;
    private final CourseRepo courseRepo;

    public void create(ReviewRequest reviewRequest, String username) {
        if (Objects.isNull(reviewRequest.getCourseId())) {
            throw new BadRequestException("Course id is required");
        }

        if (Objects.isNull(reviewRequest.getRating())) {
            throw new BadRequestException("Rating is required");
        }

        if (Objects.isNull(reviewRequest.getReview())) {
            throw new BadRequestException("Review is required");
        }

        StudentCourse studentCourse = authService.authorized(username, reviewRequest.getCourseId());
        if (reviewRepo.existsByStudentAndPublishedCourse(studentCourse.getStudent(), studentCourse.getCourse().getPublishedCourse())) {
            throw new ConflictException("You have already reviewed");
        }

        PublishedCourse publishedCourse = studentCourse.getCourse().getPublishedCourse();
        Double newTotalScore = publishedCourse.getTotalScore() + reviewRequest.getRating();
        Long newTotalUser = publishedCourse.getTotalUser() + 1;
        Double newStar = newTotalScore / newTotalUser;
        publishedCourse.setTotalScore(newTotalScore);
        publishedCourse.setTotalUser(newTotalUser);
        publishedCourse.setStar(newStar);
        publishedCourseRepo.save(publishedCourse);

        Review review = Review.builder()
            .rating(reviewRequest.getRating())
            .comment(reviewRequest.getReview())
            .reviewDateTime(LocalDateTime.now())
            .visible(true)
            .student(studentCourse.getStudent())
            .publishedCourse(publishedCourse)
            .build();
        reviewRepo.save(review);
    }

    // public void create(ReviewRequest reviewRequest, String username) {

    //     Optional<Course> courseOptional = courseRepo.findById(reviewRequest.getCourseId());
    //     if (courseOptional.isEmpty()) {
    //         throw new MyException("review.course.id.not.found", HttpStatus.BAD_REQUEST);
    //     }

    //     AppUser appUser = appUserRepo.findByUsername(username);
    //     Student student = appUser.getStudent();
    //     // todo : uncomment later.
    //     // Long reviewId = student.getReview().getId();

    //     // if (Objects.nonNull(reviewId)) {
    //     //     throw new MyException("review.duplicate", HttpStatus.CONFLICT);
    //     // }

    //     Course course = courseOptional.get();
    //     PublishedCourse publishedCourse = course.getPublishedCourse();
    //     Double newTotalScore = publishedCourse.getTotalScore() + reviewRequest.getRating();
    //     Long newTotalUser = publishedCourse.getTotalUser() + 1;
    //     Double newStar = newTotalScore / newTotalUser;
    //     publishedCourse.setTotalScore(newTotalScore);
    //     publishedCourse.setTotalUser(newTotalUser);
    //     publishedCourse.setStar(newStar);

    //     Review review = new Review();
    //     review.setComment(reviewRequest.getReview());
    //     review.setRating(reviewRequest.getRating());
    //     review.setReviewDateTime(LocalDateTime.now());
    //     review.setVisible(true);
    //     review.setStudent(student);
    //     review.setPublishedCourse(publishedCourse);

    //     publishedCourseRepo.save(publishedCourse);
    //     reviewRepo.save(review);
    // }

    public ReviewCourseResponse getReviewCourse(Long courseId, String username) {

        Optional<Course> courseOptional = courseRepo.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new MyException("review.course.id.not.found", HttpStatus.BAD_REQUEST);
        }

        Course course = courseOptional.get();
        PublishedCourse publishedCourse = course.getPublishedCourse();

        List<Review> reviews = publishedCourse.getReviews();

        List<ReviewResponse> reviewResponses = new ArrayList<>();
        Boolean isReview = false;
        Long myReviewId = null;
        for (Review review : reviews) {

            ReviewResponse reviewResponse = new ReviewResponse(review);
            reviewResponses.add(reviewResponse);

            if (review.getStudent().getAppUser().getUsername().equals(username)) {
                isReview = true;
                myReviewId = review.getId();
            }
        }

        ReviewCourseResponse response = new ReviewCourseResponse();
        response.setCourseId(courseId);
        response.setMyReviewId(myReviewId);
        response.setIsReview(isReview);
        response.setReviews(reviewResponses);
        Double roundStar =  Double.parseDouble(String.format("%.1f", publishedCourse.getStar()));
        response.setStar(roundStar);
        response.setTotalReviewUser(publishedCourse.getTotalUser());

        return response;
    }

    public ReviewResponse getReview(Long reviewId) {

        Optional<Review> reviewOptional = reviewRepo.findById(reviewId);

        if (reviewOptional.isEmpty()) {
            throw new MyException("review.not.found", HttpStatus.BAD_REQUEST);
        }

        Review review = reviewOptional.get();
        ReviewResponse response = new ReviewResponse(review);

        return response;
    }

    public void edit(Long reviewId, ReviewRequest reviewRequest) {

        Optional<Review> reviewOptional = reviewRepo.findById(reviewId);

        if (reviewOptional.isEmpty()) {
            throw new MyException("review.not.found", HttpStatus.BAD_REQUEST);
        }
        Review review = reviewOptional.get();

        Optional<Course> courseOptional = courseRepo.findById(reviewRequest.getCourseId());
        Course course = courseOptional.get();
        PublishedCourse publishedCourse = course.getPublishedCourse();

        Double newScore = publishedCourse.getTotalScore() - review.getRating();
        newScore = newScore + reviewRequest.getRating();
        Double newStar = newScore / publishedCourse.getTotalUser();
        publishedCourse.setTotalScore(newScore);
        publishedCourse.setStar(newStar);

        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getReview());
        review.setReviewDateTime(LocalDateTime.now());

        reviewRepo.save(review);
        publishedCourseRepo.save(publishedCourse);
    }

}
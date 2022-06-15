package com.ved.backend.service;

import com.ved.backend.exception.CourseNotFoundException;
import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.exception.tempException.MyException;
import com.ved.backend.model.*;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.PublishedCourseRepo;
import com.ved.backend.repo.ReviewRepo;
import com.ved.backend.request.ReviewRequest;
import com.ved.backend.response.PublishedCourseResponse;
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

    public ReviewCourseResponse getReviewsByCourseId(Long courseId, String username) {
        StudentCourse studentCourse = authService.authorized(username, courseId);
        PublishedCourse publishedCourse = publishedCourseRepo.findByCourseId(courseId)
            .orElseThrow(() -> new CourseNotFoundException(courseId));
        PublishedCourseResponse publishedCourseResponse = new PublishedCourseResponse(publishedCourse);

        Long myReviewId = null;
        List<ReviewResponse> reviewResponses = new ArrayList<ReviewResponse>();
        for (Review review : publishedCourse.getReviews()) {
            if (review.getStudent().equals(studentCourse.getStudent())) {
                myReviewId = review.getId();
            }
            reviewResponses.add(new ReviewResponse(review));
        }

        return ReviewCourseResponse.builder()
            .summary(publishedCourseResponse)
            .reviews(reviewResponses)
            .myReviewId(myReviewId)
            .build();
    }

    /* ******************************************************************************************* */

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
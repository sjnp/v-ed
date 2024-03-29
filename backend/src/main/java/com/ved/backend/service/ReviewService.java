package com.ved.backend.service;

import com.ved.backend.exception.CourseNotFoundException;
import com.ved.backend.exception.ReviewNotFoundException;
import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.model.PublishedCourse;
import com.ved.backend.model.Review;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.PublishedCourseRepo;
import com.ved.backend.repo.ReviewRepo;
import com.ved.backend.request.ReviewRequest;
import com.ved.backend.response.PublishedCourseResponse;
import com.ved.backend.response.ReviewCourseResponse;
import com.ved.backend.response.ReviewResponse;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
@Transactional
public class ReviewService {

    private final AuthService authService;

    private final ReviewRepo reviewRepo;
    private final PublishedCourseRepo publishedCourseRepo;

    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);

    public void create(ReviewRequest reviewRequest, String username) {
        log.info("Create review in course id: {} by username: {}", reviewRequest.getCourseId(), username);

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

    public ReviewCourseResponse getReviewsCourse(Long courseId, String username) {
        PublishedCourse publishedCourse = publishedCourseRepo.findByCourseId(courseId)
            .orElseThrow(() -> new CourseNotFoundException(courseId));
        PublishedCourseResponse publishedCourseResponse = new PublishedCourseResponse(publishedCourse);
        Long myReviewId = null;
        List<ReviewResponse> reviewResponses = new ArrayList<ReviewResponse>();
        for (Review review : publishedCourse.getReviews()) {

            if (review.getStudent().getAppUser().getUsername().equals(username)) {
                myReviewId = review.isVisible() == true ? review.getId() : 0L;
            }

            if (review.isVisible()) {
                reviewResponses.add(new ReviewResponse(review));
            }
        }     
        
        return ReviewCourseResponse.builder()
            .summary(publishedCourseResponse)
            .reviews(reviewResponses)
            .myReviewId(myReviewId)
            .build();
    }

    public ReviewCourseResponse getReviewsCourseByStudent(Long courseId, String username) {
        log.info("Get reviews course id: {} by username: {}", courseId, username);
        authService.authorized(username, courseId);
        return this.getReviewsCourse(courseId, username);
    }

    public ReviewCourseResponse getReviewsCourseByInstructor(Long courseId, String username) {
        log.info("Get reviews course id: {} by username: {}", courseId, username);
        authService.authorizedInstructor(username, courseId);
        return this.getReviewsCourse(courseId, username);
    }

    public ReviewResponse getReview(Long courseId, Long reviewId, String username) {
        log.info("Get review id: {} by username: {}", reviewId, username);
        authService.authorized(username, courseId);
        Review review = reviewRepo.findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        return new ReviewResponse(review);
    }

    public void edit(String username, Long reviewId, ReviewRequest reviewRequest) {
        log.info("Edit review id: {} by username: {}", reviewRequest.getReview(), username);

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
        Review review = reviewRepo.findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException(reviewId));

        PublishedCourse publishedCourse = studentCourse.getCourse().getPublishedCourse();
        Double totalScore = publishedCourse.getTotalScore() - review.getRating();
        Double newTotalScore = totalScore + reviewRequest.getRating();
        Double newStar = newTotalScore / publishedCourse.getTotalUser();
        publishedCourse.setTotalScore(newTotalScore);
        publishedCourse.setStar(newStar);
        publishedCourseRepo.save(publishedCourse);

        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getReview());
        review.setReviewDateTime(LocalDateTime.now());
        reviewRepo.save(review);
    }

}
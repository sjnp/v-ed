package com.ved.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Course;
import com.ved.backend.model.PublishedCourse;
import com.ved.backend.model.Review;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.PublishedCourseRepo;
import com.ved.backend.repo.ReviewRepo;
import com.ved.backend.request.ReviewRequest;
import com.ved.backend.response.ReviewCourseResponse;
import com.ved.backend.response.ReviewResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final AppUserRepo appUserRepo;
    private final PublishedCourseRepo publishedCourseRepo;
    private final CourseRepo courseRepo;
    private final ReviewRepo reviewRepo;
    
    public ReviewServiceImpl(
        PublishedCourseRepo publishedCourseRepo, 
        AppUserRepo appUserRepo, 
        CourseRepo courseRepo,
        ReviewRepo reviewRepo
    ) {
        this.appUserRepo = appUserRepo;
        this.publishedCourseRepo = publishedCourseRepo;
        this.courseRepo = courseRepo;
        this.reviewRepo = reviewRepo;
    }

    public void create(ReviewRequest reviewRequest, String username) {

        Optional<Course> courseOptional = courseRepo.findById(reviewRequest.getCourseId());
        if (courseOptional.isEmpty()) {
            throw new MyException("review.course.id.not.found", HttpStatus.BAD_REQUEST);
        }

        AppUser appUser = appUserRepo.findByUsername(username);
        Student student = appUser.getStudent();
        // todo : uncomment later.
        // Long reviewId = student.getReview().getId();

        // if (Objects.nonNull(reviewId)) {
        //     throw new MyException("review.duplicate", HttpStatus.CONFLICT);
        // }

        Course course = courseOptional.get();
        PublishedCourse publishedCourse = course.getPublishedCourse();
        Double newTotalScore = publishedCourse.getTotalScore() + reviewRequest.getRating();
        Long newTotalUser = publishedCourse.getTotalUser() + 1;
        Double newStar = newTotalScore / newTotalUser;
        publishedCourse.setTotalScore(newTotalScore);
        publishedCourse.setTotalUser(newTotalUser);
        publishedCourse.setStar(newStar);

        Review review = new Review();
        review.setComment(reviewRequest.getReview());
        review.setRating(reviewRequest.getRating());
        review.setReviewDateTime(LocalDateTime.now());
        review.setVisible(true);
        review.setStudent(student);
        review.setPublishedCourse(publishedCourse);

        publishedCourseRepo.save(publishedCourse);
        reviewRepo.save(review);
    }

    public ReviewCourseResponse getReviewCourse(Long courseId, String username) {

        Optional<Course> courseOptional = courseRepo.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new MyException("review.course.id.not.found", HttpStatus.BAD_REQUEST);
        }

        Course course = courseOptional.get();
        PublishedCourse publishedCourse = course.getPublishedCourse();

        List<Review> reviews = publishedCourse.getReviews();

        List<ReviewResponse> reviewResponses = new ArrayList<ReviewResponse>();
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
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getReview());
        review.setReviewDateTime(LocalDateTime.now());

        reviewRepo.save(review);
    }

}
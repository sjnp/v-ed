package com.ved.backend.controller;

import com.ved.backend.model.Instructor;
import com.ved.backend.request.AnswerRequest;
import com.ved.backend.request.BuyCourseRequest;
import com.ved.backend.request.CommentRequest;
import com.ved.backend.request.PostRequest;
import com.ved.backend.request.ReportRequest;
import com.ved.backend.request.ReviewRequest;
import com.ved.backend.response.*;
import com.ved.backend.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/students")
public class StudentController {

  private final StudentService studentService;
  private final ReviewService reviewService;

  private final StudentCourseService studentCourseService;
  private final CourseService courseService;
  private final AssignmentService assignmentService;
  private final PostService postService;
  private final ReportService reportService;

  @PostMapping("/course/free") // fixxxx
  public ResponseEntity<?> buyFreeCourse(@RequestBody BuyCourseRequest buyCourseRequest, Principal principal) {
    studentCourseService.buyFreeCourse(principal.getName(), buyCourseRequest.getCourseId());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // TODO: Add logic for show overview my course 4 course card response 
  @GetMapping("/course-samples")
  public ResponseEntity<List<CourseCardResponse>> getCourseSamples(Principal principal) {
    List<CourseCardResponse> response = studentCourseService.getMyCourses(principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses")
  public ResponseEntity<List<CourseCardResponse>> getMyCourses(Principal principal) {
    List<CourseCardResponse> response = studentCourseService.getMyCourses(principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}")
  public ResponseEntity<CourseResponse> getCourse(@PathVariable Long courseId, Principal principal) {
    CourseResponse response = courseService.getCourse(courseId, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/video")
  public ResponseEntity<VideoResponse> getVideoUrl(@PathVariable Long courseId, @PathVariable int chapterIndex, @PathVariable int sectionIndex, Principal principal) {
    VideoResponse response = courseService.getVideoUrl(courseId, chapterIndex, sectionIndex, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/handout/{handoutIndex}")
  public ResponseEntity<String> getHandoutUrl(@PathVariable Long courseId, @PathVariable int chapterIndex, @PathVariable int sectionIndex, @PathVariable int handoutIndex, Principal principal) {
    String response = courseService.getHandoutUrl(courseId, chapterIndex, sectionIndex, handoutIndex, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/chapter/{chapterIndex}/answer")
  public ResponseEntity<List<AssignmentAnswerResponse>> getAssignmentAnswer(@PathVariable Long courseId, @PathVariable Integer chapterIndex, Principal principal) {
    List<AssignmentAnswerResponse> response = assignmentService.getAssignmentAnswer(courseId, chapterIndex, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/chapter/{chapterIndex}/no/{noIndex}/answer/{fileName}")
  public ResponseEntity<String> getUploadAnswerUrl(@PathVariable Long courseId, @PathVariable int chapterIndex, @PathVariable int noIndex, @PathVariable String fileName, Principal principal) {
    String response = assignmentService.getUploadAnswerUrl(courseId, chapterIndex, noIndex, fileName, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/courses/{courseId}/answer")
  public ResponseEntity<?> createAnswer(@RequestBody AnswerRequest answerRequest, Principal principal) {
    assignmentService.createAnswer(answerRequest, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/courses/post")
  public ResponseEntity<CreatePostResponse> createPost(@RequestBody PostRequest postRequest, Principal principal) {
    CreatePostResponse response = postService.createPost(postRequest, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/courses/{courseId}/posts")
  public ResponseEntity<List<PostCardResponse>> getAllPosts(@PathVariable Long courseId, Principal principal) {
    List<PostCardResponse> response = postService.getPostsByCourseId(principal.getName(), courseId);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/posts/{postId}")
  public ResponseEntity<PostCommentResponse> getPostById(@PathVariable Long courseId, @PathVariable Long postId, Principal principal) {
    PostCommentResponse response = postService.getPostById(principal.getName(), courseId, postId);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/courses/{courseId}/posts/{postId}/comment")
  public ResponseEntity<CommentResponse> createComment(@PathVariable Long courseId, @PathVariable Long postId, @RequestBody CommentRequest commentRequest, Principal principal) {
    CommentResponse response = postService.createComment(principal.getName(), courseId, postId, commentRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/courses/review")
  public ResponseEntity<?> createReview(@RequestBody ReviewRequest reviewRequest, Principal principal) {
    reviewService.create(reviewRequest, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/courses/{courseId}/reviews")
  public ResponseEntity<ReviewCourseResponse> getReviewsByCourseId(@PathVariable Long courseId, Principal principal) {
    ReviewCourseResponse response = reviewService.getReviewsByCourseId(courseId, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/reviews/{reviewId}")
  public ResponseEntity<ReviewResponse> getReview(@PathVariable Long courseId, @PathVariable Long reviewId, Principal principal) {
    ReviewResponse response = reviewService.getReview(courseId, reviewId, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/courses/reviews/{reviewId}")
  public ResponseEntity<?> editReview(@PathVariable Long reviewId, @RequestBody ReviewRequest reviewRequest, Principal principal) {
    reviewService.edit(principal.getName(), reviewId, reviewRequest);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/courses/{courseId}/about")
  public ResponseEntity<AboutCourseResponse> getAboutCourse(@PathVariable Long courseId, Principal principal) {
    AboutCourseResponse response = courseService.getAboutCourse(courseId, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  /* *************************************************************************************************** */

  @GetMapping("/reason-reports")
  public ResponseEntity<List<ReasonReportResponse>> getReasonReports() {
    List<ReasonReportResponse> response = reportService.getReasonReports();
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/report")
  public ResponseEntity<?> createReport(@RequestBody ReportRequest reportRequest, Principal principal) {
    reportService.createReport(principal.getName(), reportRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // ------------------------------------------------------------------------------------------------------
  @PutMapping(path = "/instructor-feature")
  public ResponseEntity<?> changeStudentIntoInstructor(@RequestBody Instructor instructor, Principal principal) {
    studentService.changeRoleFromStudentIntoInstructor(instructor, principal.getName());
    return ResponseEntity.ok().build();
  }

}
package com.ved.backend.controller;

import com.ved.backend.model.Instructor;
import com.ved.backend.request.AnswerRequest;
import com.ved.backend.request.PostRequest;
import com.ved.backend.request.ReviewRequest;
import com.ved.backend.response.*;
import com.ved.backend.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/students")
public class StudentController {

  private final StudentService studentService;
  private final CourseService courseService;
  private final PostService postService;
  private final CommentService commentService;
  private final ReviewService reviewService;
  private final StudentCourseService studentCourseService;

  @PutMapping(path = "/instructor-feature")
  public ResponseEntity<?> changeStudentIntoInstructor(@RequestBody Instructor instructor, Principal principal) {
    studentService.changeRoleFromStudentIntoInstructor(instructor, principal.getName());
    return ResponseEntity.ok().build();
  }

  //////////////////////////////////////////////////////////////////

  @GetMapping("/courses")
  public ResponseEntity<List<CourseCardResponse>> getMyCourse(Principal principal) {
    // List<CourseCardResponse> response = studentService.getMyCourse(principal.getName());
    List<CourseCardResponse> response = studentCourseService.getMyCourses(principal.getName());
    return ResponseEntity.ok().body(response);
  }

  // REFACTOR: moved from OverviewController
  // TODO: Add logic for show overview my course 4 course card response 
  @GetMapping("/course-samples")
  public ResponseEntity<List<CourseCardResponse>> getCourseSamples(Principal principal) {
    // List<CourseCardResponse> response = studentService.getMyCourse(principal.getName());
    List<CourseCardResponse> response = studentCourseService.getMyCourses(principal.getName());
    return ResponseEntity.ok().body(response);
  }

  /* *************************************************************** */

  @PostMapping("/free/course")
  public ResponseEntity<?> buyFreeCourse(@RequestBody Long courseId, Principal principal) {
    studentService.buyFreeCourse(courseId, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/courses/{courseId}")
  public ResponseEntity<CourseResponse> getCourse(@PathVariable Long courseId, Principal principal) {
    CourseResponse response = studentService.getCourse(courseId, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/video")
  public ResponseEntity<VideoResponse> getVideoCourseUrl(@PathVariable Long courseId, @PathVariable int chapterIndex, @PathVariable int sectionIndex, Principal principal) {
    VideoResponse response = studentService.getVideoCourseUrl(courseId, chapterIndex, sectionIndex, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/handout/{handoutIndex}")
  public ResponseEntity<String> getHandoutUrl(@PathVariable Long courseId, @PathVariable int chapterIndex, @PathVariable int sectionIndex, @PathVariable int handoutIndex, Principal principal) {
    String response = studentService.getHandoutUrl(courseId, chapterIndex, sectionIndex, handoutIndex, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/chapter/{chapterIndex}/no/{noIndex}/answer/{fileName}")
  public ResponseEntity<String> getAnswerUploadUrl(@PathVariable Long courseId, @PathVariable int chapterIndex, @PathVariable int noIndex, @PathVariable String fileName, Principal principal) {
    String response = studentService.getUploadAnswerUrl(courseId, chapterIndex, noIndex, fileName, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/courses/{courseId}/answer")
  public ResponseEntity<?> createAnswer(@RequestBody AnswerRequest answerRequest, Principal principal) {
    studentService.createAnswer(answerRequest, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/courses/{courseId}/chapter/{chapterIndex}/answer")
  public ResponseEntity<List<AssignmentAnswerResponse>> getAssignmentAnswer(@PathVariable Long courseId, @PathVariable Integer chapterIndex, Principal principal) {
    List<AssignmentAnswerResponse> response = studentService.getAssignmentAnswer(courseId, chapterIndex, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  /* *************************************************************************************************** */

  @PostMapping("/courses/post")
  public ResponseEntity<CreatePostResponse> createPost(@RequestBody PostRequest postRequest, Principal principal) {
    CreatePostResponse response = studentService.createPost(postRequest, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // ------------------------------------------------------------------------------------------------------

  @GetMapping("/courses/{courseId}/about")
  public ResponseEntity<AboutCourseResponse> getAboutCourse(@PathVariable Long courseId) {
    AboutCourseResponse response = courseService.getAboutCourse(courseId);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/posts")
  public ResponseEntity<List<PostResponse>> getAllPosts(@PathVariable Long courseId) {
    List<PostResponse> response = postService.getPostByCourseId(courseId);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/posts/{postId}")
  public ResponseEntity<PostResponse> getPost(@PathVariable Long courseId,
                                                       @PathVariable Long postId,
                                                       Principal principal) {
    PostResponse response = postService.getPostById(postId);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/reviews")
  public ResponseEntity<ReviewCourseResponse> getAllReviews(@PathVariable Long courseId, Principal principal) {
    ReviewCourseResponse response = reviewService.getReviewCourse(courseId, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/reviews/{reviewId}")
  public ResponseEntity<ReviewResponse> getReview(@PathVariable Long courseId,
                                                  @PathVariable Long reviewId,
                                                  Principal principal) {
    ReviewResponse response = reviewService.getReview(reviewId);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/courses/{courseId}/posts/comment")
  public ResponseEntity<CommentResponse> createComment(@PathVariable Long courseId,
                                                       @RequestBody HashMap<String, Object> bodyRequest,
                                                       Principal principal) {
    Long questionBoardId = Long.parseLong(String.valueOf(bodyRequest.get("questionId")));
    String comment = String.valueOf(bodyRequest.get("comment"));
    String username = principal.getName();
    CommentResponse response = commentService.create(questionBoardId, comment, username);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/courses/review")
  public ResponseEntity<?> createReview(@RequestBody ReviewRequest reviewRequest, Principal principal) {
    reviewService.create(reviewRequest, principal.getName());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/courses/reviews/{reviewId}")
  public ResponseEntity<?> editReview(@PathVariable Long reviewId, @RequestBody ReviewRequest reviewRequest) {
    reviewService.edit(reviewId, reviewRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
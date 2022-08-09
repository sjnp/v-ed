package com.ved.backend.controller;

import com.ved.backend.model.Course;
import com.ved.backend.request.CommentRequest;
import com.ved.backend.response.AssignmentChapterResponse;
import com.ved.backend.response.AssignmentCourseResponse;
import com.ved.backend.response.CommentResponse;
import com.ved.backend.response.IncompleteCourseResponse;
import com.ved.backend.response.PostCardResponse;
import com.ved.backend.response.PostCommentResponse;
import com.ved.backend.response.PublishedCourseInfoResponse;
import com.ved.backend.response.ReviewCourseResponse;
import com.ved.backend.service.AssignmentService;
import com.ved.backend.service.InstructorService;
import com.ved.backend.service.PostService;
import com.ved.backend.service.ReviewService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/instructors")
public class InstructorController {

  private final InstructorService instructorService;
  private final ReviewService reviewService;
  private final PostService postService;
  private final AssignmentService assignmentService;

  @GetMapping(path = "/incomplete-courses/{courseId}")
  public ResponseEntity<?> getIncompleteCourse(@PathVariable Long courseId, Principal principal) {
    IncompleteCourseResponse incompleteCourseMaterials = instructorService.getIncompleteCourseDetails(courseId, principal.getName());
    return ResponseEntity.ok().body(incompleteCourseMaterials);
  }

  @GetMapping(path = "/incomplete-courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/video/{fileName}/pre-authenticated-request")
  public ResponseEntity<?> createParToReadVideoFromIncompleteCourse(@PathVariable Long courseId,
                                                                    @PathVariable Long chapterIndex,
                                                                    @PathVariable Long sectionIndex,
                                                                    @PathVariable String fileName,
                                                                    Principal principal) {
    Map<String, String> preauthenticatedRequest = instructorService
        .createParToReadCourseVideoFromIncompleteCourse(courseId,
            chapterIndex,
            sectionIndex,
            fileName,
            principal.getName());
    URI uri = URI.create(ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path(preauthenticatedRequest.get("preauthenticatedRequestUrl"))
        .toUriString());
    return ResponseEntity.created(uri).body(preauthenticatedRequest);
  }

  @GetMapping(path = "/incomplete-courses")
  public ResponseEntity<?> getAllIncompleteCourses(Principal principal) {
    HashMap<String, Object> dto = instructorService.getAllIncompleteCourses(principal.getName());
    return ResponseEntity.ok().body(dto);
  }

  @GetMapping(path = "/pending-courses")
  public ResponseEntity<?> getAllPendingCourses(Principal principal) {
    HashMap<String, Object> pendingCoursesJson = instructorService.getAllPendingCourses(principal.getName());
    return ResponseEntity.ok().body(pendingCoursesJson);
  }

  @GetMapping(path = "/approved-courses")
  public ResponseEntity<?> getAllApprovedCourses(Principal principal) {
    HashMap<String, Object> approvedCoursesJson = instructorService.getAllApprovedCourses(principal.getName());
    return ResponseEntity.ok().body(approvedCoursesJson);
  }

  @GetMapping(path = "/rejected-courses")
  public ResponseEntity<?> getAllRejectedCourses(Principal principal) {
    HashMap<String, Object> rejectedCoursesJson = instructorService.getAllRejectedCourses(principal.getName());
    return ResponseEntity.ok().body(rejectedCoursesJson);
  }

  @GetMapping(path = "/published-courses")
  public ResponseEntity<?> getAllPublishedCourses(Principal principal) {
    List<PublishedCourseInfoResponse> publishedCourseInfo = instructorService.getAllPublishedCourses(principal.getName());
    return ResponseEntity.ok().body(publishedCourseInfo);
  }

  @PostMapping(path = "/course")
  public ResponseEntity<?> createCourse(@RequestBody Course course, Principal principal) {
    HashMap<String, Long> payload = instructorService.createCourse(course, principal.getName());
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/api/instructors/incomplete-courses/" + payload.get("id"))
        .toUriString());
    return ResponseEntity.created(uri).body(payload);
  }

  @PostMapping(path = "/incomplete-courses/{courseId}/picture/pre-authenticated-request")
  public ResponseEntity<?> createParToUploadPicture(@PathVariable Long courseId,
                                                    @RequestBody String fileName,
                                                    Principal principal) {
    Map<String, String> payload = instructorService
        .createParToUploadCoursePicture(courseId, fileName, principal.getName());
    URI uri = URI.create(ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path(payload.get("preauthenticatedRequestUrl"))
        .toUriString());
    return ResponseEntity.created(uri).body(payload);
  }

  @PostMapping(path = "/incomplete-courses/{courseId}/video/pre-authenticated-request")
  public ResponseEntity<?> createParToUploadVideo(@PathVariable Long courseId,
                                                  @RequestBody HashMap<String, Object> requestData,
                                                  Principal principal) {
    Map<String, String> preauthenticatedRequest = instructorService
        .createParToUploadCourseVideo(courseId,
            Long.parseLong(String.valueOf(requestData.get("chapterIndex"))),
            Long.parseLong(String.valueOf(requestData.get("sectionIndex"))),
            String.valueOf(requestData.get("fileName")),
            principal.getName());
    URI uri = URI.create(ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path(preauthenticatedRequest.get("preauthenticatedRequestUrl"))
        .toUriString());
    return ResponseEntity.created(uri).body(preauthenticatedRequest);
  }

  @PostMapping(path = "/incomplete-courses/{courseId}/handout/pre-authenticated-request")
  public ResponseEntity<?> createParToUploadHandout(@PathVariable Long courseId,
                                                    @RequestBody HashMap<String, Object> requestData,
                                                    Principal principal) {
    Map<String, String> preauthenticatedRequest = instructorService
        .createParToUploadHandout(courseId,
            Long.parseLong(String.valueOf(requestData.get("chapterIndex"))),
            Long.parseLong(String.valueOf(requestData.get("sectionIndex"))),
            String.valueOf(requestData.get("fileName")),
            principal.getName());
    URI uri = URI.create(ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path(preauthenticatedRequest.get("preauthenticatedRequestUrl"))
        .toUriString());
    return ResponseEntity.created(uri).body(preauthenticatedRequest);
  }

  @PutMapping(path = "/incomplete-courses/{courseId}/picture/{pictureName}")
  public ResponseEntity<?> saveCoursePictureUrl(@PathVariable Long courseId, @PathVariable String pictureName, Principal principal) {
    Map<String, String> payload = instructorService.saveCoursePictureUrl(courseId, pictureName, principal.getName());
    URI uri = URI.create(ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path(payload.get("pictureUrl"))
        .toUriString());
    return ResponseEntity.created(uri).body(payload);
  }

  @PutMapping(path = "/incomplete-courses/{courseId}/chapters")
  public ResponseEntity<?> updateCourseMaterial(@PathVariable Long courseId,
                                                @RequestBody Course course,
                                                Principal principal) {
    instructorService.updateCourseMaterials(courseId, course, principal.getName());
    return ResponseEntity.ok().build();
  }

  @PutMapping(path = "/incomplete-courses/{courseId}/state")
  public ResponseEntity<?> submitIncompleteCourse(@PathVariable Long courseId,
                                                  Principal principal) {
    instructorService.submitIncompleteCourse(courseId, principal.getName());
    return ResponseEntity.ok().build();
  }

  @PutMapping(path = "/approved-courses/{courseId}")
  public ResponseEntity<?> publishApprovedCourse(@PathVariable Long courseId, Principal principal) {
    instructorService.publishApprovedCourse(courseId, principal.getName());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(path = "/incomplete-courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/handout/{handoutObjectName}")
  public ResponseEntity<?> deleteHandout(@PathVariable Long courseId,
                                         @PathVariable Integer chapterIndex,
                                         @PathVariable Integer sectionIndex,
                                         @PathVariable String handoutObjectName,
                                         Principal principal) {
    instructorService.deleteHandout(courseId, handoutObjectName, principal.getName());
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(path = "/incomplete-courses/{courseId}/picture")
  public ResponseEntity<?> deleteCoursePictureUrl(@PathVariable Long courseId,
                                                  Principal principal) {
    instructorService.deleteCoursePictureUrl(courseId, principal.getName());
    return ResponseEntity.ok().build();
  }

  @GetMapping("/courses/{courseId}/reviews")
  public ResponseEntity<ReviewCourseResponse> getAllReviewsCourse(@PathVariable Long courseId, Principal principal) {
    ReviewCourseResponse response = reviewService.getReviewsCourseByInstructor(courseId, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/posts")
  public ResponseEntity<List<PostCardResponse>> getAllPostsCourse(@PathVariable Long courseId, Principal principal) {
    List<PostCardResponse> response = postService.getAllPostsCourseByInstructor(courseId, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/posts/{postId}")
  public ResponseEntity<PostCommentResponse> getPostById(@PathVariable Long courseId, @PathVariable Long postId, Principal principal) {
    PostCommentResponse response = postService.getPostByInstructor(principal.getName(), courseId, postId);
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/courses/{courseId}/posts/{postId}/comment")
  public ResponseEntity<List<CommentResponse>> createComment(@PathVariable Long courseId, @PathVariable Long postId, @RequestBody CommentRequest commentRequest, Principal principal) {
    List<CommentResponse> response = postService.createCommentByInstructor(principal.getName(), courseId, postId, commentRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /////////////////////////////////////////////////////////////

  @GetMapping("/courses/{courseId}/assignments")
  public ResponseEntity<List<AssignmentCourseResponse>> getAssignmentsCourse(@PathVariable Long courseId, Principal principal) {
    List<AssignmentCourseResponse> response = assignmentService.getAssignmentCourse(courseId, principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/courses/{courseId}/assignments/chapters/{chapterIndex}")
  public ResponseEntity<List<AssignmentChapterResponse>> getAssignmentChapter(@PathVariable Long courseId, @PathVariable int chapterIndex, Principal principal) {
    List<AssignmentChapterResponse> response = assignmentService.getAssignmentChapter(courseId, chapterIndex, principal.getName());
    return ResponseEntity.ok().body(response);
  }

}
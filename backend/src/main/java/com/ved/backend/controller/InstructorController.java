package com.ved.backend.controller;

import com.ved.backend.model.Course;
import com.ved.backend.response.IncompleteCourseResponse;
import com.ved.backend.response.PublishedCourseInfoResponse;
import com.ved.backend.response.ReviewCourseResponse;
import com.ved.backend.service.InstructorService;
import com.ved.backend.service.ReviewService;

import lombok.AllArgsConstructor;
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

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorController.class);

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
  public ResponseEntity<ReviewCourseResponse> getReviewsCourse(@PathVariable Long courseId, Principal principal) {
    ReviewCourseResponse response = reviewService.getReviewsCourseByInstructor(courseId, principal.getName());
    return ResponseEntity.ok().body(response);
  }

}
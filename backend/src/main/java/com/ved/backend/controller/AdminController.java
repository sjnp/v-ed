package com.ved.backend.controller;

import com.ved.backend.service.CourseService;
import com.ved.backend.service.PrivateObjectStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/admins")
public class AdminController {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdminController.class);

  private final CourseService courseService;

  @GetMapping(path = "/pending-courses")
  public ResponseEntity<?> getPendingCourses() {
    List<Map<String, Object>> pendingCoursesJson = courseService.getPendingCourses();
    return ResponseEntity.ok().body(pendingCoursesJson);
  }

  @GetMapping(path = "/pending-courses", params = "id")
  public ResponseEntity<?> getPendingCourse(@RequestParam(name = "id") Long courseId) {
    try {
      Map<String, Object> courseJson = courseService.getPendingCourse(courseId);
      return ResponseEntity.ok().body(courseJson);
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(path = "/pending-courses")
  public ResponseEntity<?> changePendingCourseState(@RequestParam(name = "id") Long courseId,
                                                    @RequestParam(name = "isApproved") Boolean isApproved) {
    try {
      courseService.changePendingCourseState(courseId, isApproved);
      return ResponseEntity.ok().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(path = "/pending-courses/video")
  public ResponseEntity<?> getVideoFromPendingCourse(@RequestParam(name = "id") Long courseId,
                                                     @RequestParam(name = "chapterIndex") Integer chapterIndex,
                                                     @RequestParam(name = "sectionIndex") Integer sectionIndex,
                                                     @RequestParam(name = "videoUri") String videoUri,
                                                     Principal principal) {
    try {
      String videoUrl = courseService.getVideoUriFromPendingCourse(courseId, chapterIndex, sectionIndex, videoUri, principal.getName());
      Map<String, String> responseJson = new HashMap<>();
      responseJson.put("videoUrl", videoUrl);
      return ResponseEntity.ok().body(responseJson);
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(path = "/pending-courses/handout")
  public ResponseEntity<?> getHandoutFromPendingCourse(@RequestParam(name = "id") Long courseId,
                                                       @RequestParam(name = "chapterIndex") Integer chapterIndex,
                                                       @RequestParam(name = "sectionIndex") Integer sectionIndex,
                                                       @RequestParam(name = "handoutUri") String handoutUri,
                                                       Principal principal) {
    try {
      String handoutUrl = courseService
          .getHandoutUrlFromPendingCourse(courseId, chapterIndex, sectionIndex, handoutUri, principal.getName());
      Map<String, String> responseJson = new HashMap<>();
      responseJson.put("handoutUrl", handoutUrl);
      return ResponseEntity.ok().body(responseJson);
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  public AdminController(CourseService courseService) {
    this.courseService = courseService;

  }
}

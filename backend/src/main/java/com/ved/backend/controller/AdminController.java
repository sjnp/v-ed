package com.ved.backend.controller;

import com.ved.backend.service.CourseService;
import com.ved.backend.service.PrivateObjectStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/admins")
public class AdminController {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdminController.class);

  private final CourseService courseService;

  @GetMapping(path = "/pending-courses")
  public ResponseEntity<?> getAllPendingCourses() {
    List<Map<String, Object>> pendingCoursesJson = courseService.getPendingCourses();
    return ResponseEntity.ok().body(pendingCoursesJson);
  }

  @GetMapping(path = "/pending-courses/{courseId}")
  public ResponseEntity<?> getPendingCourse(@PathVariable Long courseId) {
    try {
      Map<String, Object> courseJson = courseService.getPendingCourse(courseId);
      return ResponseEntity.ok().body(courseJson);
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(path = "/pending-courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/video")
  public ResponseEntity<?> getVideoFromPendingCourse(@PathVariable Long courseId,
                                                     @PathVariable Integer chapterIndex,
                                                     @PathVariable Integer sectionIndex,
                                                     Principal principal) {
    try {
      String videoUrl = courseService.getVideoUriFromPendingCourse(courseId, chapterIndex, sectionIndex, principal.getName());
      Map<String, String> responseJson = new HashMap<>();
      responseJson.put("videoUrl", videoUrl);
      return ResponseEntity.ok().body(responseJson);
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(path = "/pending-courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/handout/{handoutUri}")
  public ResponseEntity<?> getHandoutFromPendingCourse(@PathVariable Long courseId,
                                                       @PathVariable Integer chapterIndex,
                                                       @PathVariable Integer sectionIndex,
                                                       @PathVariable String handoutUri,
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

  @PutMapping(path = "/pending-courses/{courseId}", params = "isApproved")
  public ResponseEntity<?> changePendingCourseState(@PathVariable Long courseId,
                                                    @RequestParam(name = "isApproved") Boolean isApproved) {
    try {
      courseService.changePendingCourseState(courseId, isApproved);
      return ResponseEntity.ok().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

}

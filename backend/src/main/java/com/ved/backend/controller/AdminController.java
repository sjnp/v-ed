package com.ved.backend.controller;

import com.ved.backend.response.FullPendingCourseInfoDto;
import com.ved.backend.response.PendingCourseResponse;
import com.ved.backend.response.VideoResponse;
import com.ved.backend.service.AdminService;
import com.ved.backend.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  private final AdminService adminService;

  @GetMapping(path = "/pending-courses")
  public ResponseEntity<?> getAllPendingCourses(Principal principal) {
    List<PendingCourseResponse> pendingCourseResponses = adminService.getPendingCourses(principal.getName());
    return ResponseEntity.ok().body(pendingCourseResponses);
  }

  @GetMapping(path = "/pending-courses/{courseId}")
  public ResponseEntity<?> getPendingCourse(@PathVariable Long courseId, Principal principal) {
    FullPendingCourseInfoDto fullPendingCourseInfoDto = adminService.getPendingCourse(courseId, principal.getName());
    return ResponseEntity.ok().body(fullPendingCourseInfoDto);
  }

  @GetMapping(path = "/pending-courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/video")
  public ResponseEntity<?> getVideoFromPendingCourse(@PathVariable Long courseId,
                                                     @PathVariable Integer chapterIndex,
                                                     @PathVariable Integer sectionIndex,
                                                     Principal principal) {
    VideoResponse videoResponse = adminService.getVideoUrlFromPendingCourse(courseId,
        chapterIndex,
        sectionIndex,
        principal.getName());
    return ResponseEntity.ok().body(videoResponse);
  }

  @GetMapping(path = "/pending-courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/handout/{handoutUri}")
  public ResponseEntity<?> getHandoutFromPendingCourse(@PathVariable Long courseId,
                                                       @PathVariable Integer chapterIndex,
                                                       @PathVariable Integer sectionIndex,
                                                       @PathVariable String handoutUri,
                                                       Principal principal) {
    Map<String, String> handoutResponse = adminService.getHandoutUrlFromPendingCourse(courseId,
        chapterIndex,
        sectionIndex,
        handoutUri,
        principal.getName());
    return ResponseEntity.ok().body(handoutResponse);
  }

  @PutMapping(path = "/pending-courses/{courseId}", params = "isApproved")
  public ResponseEntity<?> changePendingCourseState(@PathVariable Long courseId,
                                                    @RequestParam(name = "isApproved") Boolean isApproved,
                                                    Principal principal) {
    adminService.changePendingCourseState(courseId, isApproved, principal.getName());
    return ResponseEntity.ok().build();
  }

}

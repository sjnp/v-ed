package com.ved.backend.controller;

import com.ved.backend.response.*;
import com.ved.backend.service.AdminReportService;
import com.ved.backend.service.AdminService;
import com.ved.backend.service.CourseService;
import com.ved.backend.service.ReportService;
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

  private final AdminService adminService;
  private final AdminReportService adminReportService;
  private final ReportService reportService;

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

  @GetMapping(path = "/pending-reports/reviews")
  public ResponseEntity<?> getAllPendingReviewReports(Principal principal) {
    List<PendingReviewReportResponse> pendingReviewReportResponses = adminReportService.getAllPendingReviewReports(principal.getName());
    return ResponseEntity.ok().body(pendingReviewReportResponses);
  }

  @GetMapping(path = "/report-reasons")
  public ResponseEntity<?> getAllReportReasons() {
    List<ReasonReportResponse> response = reportService.getReasonReports();
    return ResponseEntity.ok().body(response);
  }

  @PutMapping(path = "/pending-courses/{courseId}", params = "isApproved")
  public ResponseEntity<?> changePendingCourseState(@PathVariable Long courseId,
                                                    @RequestParam(name = "isApproved") Boolean isApproved,
                                                    Principal principal) {
    adminService.changePendingCourseState(courseId, isApproved, principal.getName());
    return ResponseEntity.ok().build();
  }

  @PutMapping(path = "/pending-reports/reviews/{reviewReportId}", params = "isApproved")
  public ResponseEntity<?> changePendingReviewReportState(@PathVariable Long reviewReportId,
                                                          @RequestParam(name = "isApproved") Boolean isApproved,
                                                          Principal principal) {
    adminReportService.changePendingReviewReportState(reviewReportId, isApproved, principal.getName());
    return ResponseEntity.ok().build();
  }

}

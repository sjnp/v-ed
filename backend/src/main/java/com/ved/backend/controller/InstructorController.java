package com.ved.backend.controller;

import com.ved.backend.model.Course;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.service.InstructorService;
import com.ved.backend.service.PrivateObjectStorageService;
import com.ved.backend.service.PublicObjectStorageService;
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
  private final PublicObjectStorageService publicObjectStorageService;
  private final PrivateObjectStorageService privateObjectStorageService;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorController.class);

  @GetMapping(path = "/incomplete-courses/{courseId}")
  public ResponseEntity<?> getIncompleteCourse(@PathVariable Long courseId, Principal principal) {
    try {
      CourseRepo.CourseMaterials incompleteCourseMaterials = instructorService.getIncompleteCourse(courseId, principal.getName());
      return ResponseEntity.ok().body(incompleteCourseMaterials);
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(path = "/incomplete-courses")
  public ResponseEntity<?> getAllIncompleteCourses(Principal principal) {
    try {
     HashMap<String, Object> incompleteCoursesJson = instructorService.getAllIncompleteCourses(principal.getName());
     return ResponseEntity.ok().body(incompleteCoursesJson);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }

  @GetMapping(path = "/pending-courses")
  public ResponseEntity<?> getAllPendingCourses(Principal principal) {
    try {
      HashMap<String, Object> pendingCoursesJson = instructorService.getAllPendingCourses(principal.getName());
      return ResponseEntity.ok().body(pendingCoursesJson);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }

  @GetMapping(path = "/approved-courses")
  public ResponseEntity<?> getAllApprovedCourses(Principal principal) {
    try {
      HashMap<String, Object> approvedCoursesJson = instructorService.getAllApprovedCourses(principal.getName());
      return ResponseEntity.ok().body(approvedCoursesJson);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }

  @GetMapping(path = "/rejected-courses")
  public ResponseEntity<?> getAllRejectedCourses(Principal principal) {
    try {
      HashMap<String, Object> rejectedCoursesJson = instructorService.getAllRejectedCourses(principal.getName());
      return ResponseEntity.ok().body(rejectedCoursesJson);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }

  @GetMapping(path = "/published-courses")
  public ResponseEntity<?> getAllPublishedCourses(Principal principal) {
    try {
      List<HashMap<String, Object>> publishedCourses = instructorService.getAllPublishedCourses(principal.getName());
      return ResponseEntity.ok().body(publishedCourses);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }

  @PostMapping(path = "/course")
  public ResponseEntity<?> createCourse(@RequestBody Course course, Principal principal) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/instructors/course").toUriString());
    Long courseId = instructorService.createCourse(course, principal.getName());
    HashMap<String, Long> createdCourseId = new HashMap<>();
    createdCourseId.put("id", courseId);
    return ResponseEntity.created(uri).body(createdCourseId);
  }

  @PostMapping(path = "/incomplete-courses/{courseId}/picture/pre-authenticated-request")
  public ResponseEntity<?> createParToUploadPicture(@PathVariable Long courseId, @RequestBody String fileName, Principal principal) {
    try {
      String preauthenticatedRequestUrl = publicObjectStorageService.createParToUploadCoursePicture(courseId,
          fileName,
          principal.getName());
      HashMap<String, String> preauthenticatedRequest = new HashMap<>();
      preauthenticatedRequest.put("preauthenticatedRequestUrl", preauthenticatedRequestUrl);
      URI uri = URI.create(ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/api/instructors/incomplete-courses/picture/pre-authenticated-request")
          .toUriString());
      return ResponseEntity.created(uri).body(preauthenticatedRequest);
    } catch (Exception exception) {
      if (exception.getMessage().equals("Invalid file type")) {
        return ResponseEntity.badRequest().body(exception.getMessage());
      } else {
        return ResponseEntity.notFound().build();
      }
    }
  }

  @PostMapping(path = "/incomplete-courses/{courseId}/video/pre-authenticated-request")
  public ResponseEntity<?> createParToUploadVideo(@PathVariable Long courseId,
                                                  @RequestBody HashMap<String, Object> requestData,
                                                  Principal principal) {
    try {
      String preauthenticatedRequestUrl = privateObjectStorageService.createParToUploadCourseVideo(courseId,
          Long.parseLong(String.valueOf(requestData.get("chapterIndex"))),
          Long.parseLong(String.valueOf(requestData.get("sectionIndex"))),
          (String) requestData.get("fileName"),
          principal.getName());

      HashMap<String, String> preauthenticatedRequest = new HashMap<>();
      preauthenticatedRequest.put("preauthenticatedRequestUrl", preauthenticatedRequestUrl);
      URI uri = URI.create(ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/api/instructors/incomplete-courses/video/pre-authenticated-request")
          .toUriString());

      return ResponseEntity.created(uri).body(preauthenticatedRequest);
    } catch (Exception exception) {
      if (exception.getMessage().equals("Invalid file type")) {
        return ResponseEntity.badRequest().body(exception.getMessage());
      } else {
        return ResponseEntity.notFound().build();
      }
    }
  }

  @PostMapping(path = "/incomplete-courses/{courseId}/handout/pre-authenticated-request")
  public ResponseEntity<?> createParToUploadHandout(@PathVariable Long courseId,
                                                    @RequestBody HashMap<String, Object> requestData,
                                                    Principal principal) {
    try {
      String preauthenticatedRequestUrl = privateObjectStorageService.createParToUploadCourseHandout(courseId,
          Long.parseLong(String.valueOf(requestData.get("chapterIndex"))),
          Long.parseLong(String.valueOf(requestData.get("sectionIndex"))),
          (String) requestData.get("fileName"),
          principal.getName());
      HashMap<String, String> preauthenticatedRequest = new HashMap<>();
      preauthenticatedRequest.put("preauthenticatedRequestUrl", preauthenticatedRequestUrl);
      URI uri = URI.create(ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/api/instructors/incomplete-courses/handout/pre-authenticated-request")
          .toUriString());
      return ResponseEntity.created(uri).body(preauthenticatedRequest);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }



  @PutMapping(path = "/incomplete-courses/{courseId}/picture/{pictureName}")
  public ResponseEntity<?> saveCoursePictureUrl(@PathVariable Long courseId, @PathVariable String pictureName, Principal principal) {
    try {
      URI uri = URI.create(ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/api/instructors/incomplete-courses/picture")
          .toUriString());
      String pictureUrl = instructorService.saveCoursePictureUrl(courseId, pictureName, principal.getName());
      HashMap<String, String> jsonBody = new HashMap<>();
      jsonBody.put("pictureUrl", pictureUrl);
      return ResponseEntity.created(uri).body(jsonBody);
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(path = "/incomplete-courses/{courseId}/chapters")
  public ResponseEntity<?> updateCourseMaterial(@PathVariable Long courseId, @RequestBody Course course, Principal principal) {
    try {
      instructorService.updateCourseMaterials(courseId, course, principal.getName());
      return ResponseEntity.ok().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(path = "/incomplete-courses/{courseId}/state")
  public ResponseEntity<?> submitIncompleteCourse(@PathVariable Long courseId, Principal principal) {
    try {
      instructorService.submitIncompleteCourse(courseId, principal.getName());
      return ResponseEntity.ok().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(path = "/approved-courses/{courseId}")
  public ResponseEntity<?> publishApprovedCourse(@PathVariable Long courseId, Principal principal) {
    try {
      instructorService.publishApprovedCourse(courseId, principal.getName());
      return ResponseEntity.ok().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping(path = "/incomplete-courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/handout/{handoutUri}")
  public ResponseEntity<?> deleteHandout(@PathVariable Long courseId,
                                         @PathVariable Integer chapterIndex,
                                         @PathVariable Integer sectionIndex,
                                         @PathVariable String handoutUri,
                                         Principal principal) {
    try {
      privateObjectStorageService.deleteHandout(courseId, handoutUri, principal.getName());
      return ResponseEntity.noContent().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping(path = "/incomplete-courses/{courseId}/picture")
  public ResponseEntity<?> deleteCoursePictureUrl(@PathVariable Long courseId, Principal principal) {
    try {
      instructorService.deleteCoursePictureUrl(courseId, principal.getName());
      return ResponseEntity.ok().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

}

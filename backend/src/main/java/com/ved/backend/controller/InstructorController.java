package com.ved.backend.controller;

import com.ved.backend.configuration.OmiseConfigProperties;
import com.ved.backend.model.Course;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.service.InstructorService;
import com.ved.backend.service.PrivateObjectStorageService;
import com.ved.backend.service.PublicObjectStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/instructors")
public class InstructorController {
  private final InstructorService instructorService;
  private final PublicObjectStorageService publicObjectStorageService;
  private final PrivateObjectStorageService privateObjectStorageService;
  private final OmiseConfigProperties omiseKey;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorController.class);


  @GetMapping(path = "/getFinance")
  public ResponseEntity<?> getAccountData(Principal principal) {
    String response = instructorService.getOmiseAccountData(principal.getName());
    return ResponseEntity.ok(response);
  }


  @PostMapping(path = "/course")
  public ResponseEntity<?> createCourse(@RequestBody Course course, Principal principal) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/instructors/course").toUriString());
    Long courseId = instructorService.createCourse(course, principal.getName());
    HashMap<String, Long> createdCourseId = new HashMap<>();
    createdCourseId.put("id", courseId);
    return ResponseEntity.created(uri).body(createdCourseId);
  }

  @GetMapping(path = "/incomplete-courses", params = "id")
  public ResponseEntity<?> getIncompleteCourse(@RequestParam(name = "id") Long courseId, Principal principal) {
    try {
      CourseRepo.CourseMaterials incompleteCourseMaterials = instructorService.getIncompleteCourse(courseId, principal.getName());
      return ResponseEntity.ok().body(incompleteCourseMaterials);
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping(path = "/incomplete-courses")
  public ResponseEntity<?> getAllIncompleteCourse(Principal principal) {
    try {
     HashMap<String, Object> incompleteCoursesJson = instructorService.getAllIncompleteCourses(principal.getName());
     return ResponseEntity.ok().body(incompleteCoursesJson);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }

  @GetMapping(path = "/pending-courses")
  public ResponseEntity<?> getAllPendingCourse(Principal principal) {
    try {
      HashMap<String, Object> pendingCoursesJson = instructorService.getAllPendingCourses(principal.getName());
      return ResponseEntity.ok().body(pendingCoursesJson);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }

  @GetMapping(path = "/approved-courses")
  public ResponseEntity<?> getAllApprovedCourse(Principal principal) {
    try {
      HashMap<String, Object> approvedCoursesJson = instructorService.getAllApprovedCourses(principal.getName());
      return ResponseEntity.ok().body(approvedCoursesJson);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }

  @GetMapping(path = "/rejected-courses")
  public ResponseEntity<?> getAllRejectedCourse(Principal principal) {
    try {
      HashMap<String, Object> rejectedCoursesJson = instructorService.getAllRejectedCourses(principal.getName());
      return ResponseEntity.ok().body(rejectedCoursesJson);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }

  @GetMapping(path = "/published-courses")
  public ResponseEntity<?> getAllPublishedCourse(Principal principal) {
    try {
      List<HashMap<String, Object>> publishedCourses = instructorService.getAllPublishedCourses(principal.getName());
      return ResponseEntity.ok().body(publishedCourses);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }



  @PostMapping(path = "/incomplete-courses/picture/pre-authenticated-request")
  public ResponseEntity<?> createParToCreatePicture(@RequestParam(name = "id") Long courseId, @RequestBody String fileName, Principal principal) {
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

  @PostMapping(path = "/incomplete-courses/video/pre-authenticated-request")
  public ResponseEntity<?> createParToCreateVideo(@RequestParam(name = "id") Long courseId,
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

  @PostMapping(path = "/incomplete-courses/handout/pre-authenticated-request")
  public ResponseEntity<?> createParToCreateHandout(@RequestParam(name = "id") Long courseId,
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

  @DeleteMapping(path = "/incomplete-courses/handout")
  public ResponseEntity<?> deleteHandout(@RequestParam(name = "id") Long courseId,
                                         @RequestParam(name = "objectName") String objectName,
                                         Principal principal) {
    try {
      privateObjectStorageService.deleteHandout(courseId, objectName, principal.getName());
      return ResponseEntity.noContent().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(path = "/incomplete-courses/picture")
  public ResponseEntity<?> saveCoursePictureUrl(@RequestParam(name = "id") Long courseId, @RequestBody HashMap<String, String> objectData, Principal principal) {
    try {
      URI uri = URI.create(ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/api/instructors/incomplete-courses/picture")
          .toUriString());
      String pictureUrl = instructorService.saveCoursePictureUrl(courseId,
          objectData.get("objectName"),
          principal.getName());
      HashMap<String, String> jsonBody = new HashMap<>();
      jsonBody.put("pictureUrl", pictureUrl);
      return ResponseEntity.created(uri).body(jsonBody);
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(path = "/incomplete-courses/chapters")
  public ResponseEntity<?> updateCourseMaterial(@RequestParam(name = "id") Long courseId, @RequestBody Course course, Principal principal) {
    try {
      URI uri = URI.create(ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/api/instructors/incomplete-courses/video")
          .toUriString());
      instructorService.updateCourseMaterials(courseId, course, principal.getName());
      return ResponseEntity.ok().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(path = "/incomplete-courses/submission")
  public ResponseEntity<?> submitIncompleteCourse(@RequestParam(name = "id") Long courseId, Principal principal) {
    try {
      instructorService.submitIncompleteCourse(courseId, principal.getName());
      return ResponseEntity.ok().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(path = "/approved-courses")
  public ResponseEntity<?> publishApprovedCourse(@RequestParam(name = "id") Long courseId, Principal principal) {
    try {
      instructorService.publishApprovedCourse(courseId, principal.getName());
      return ResponseEntity.ok().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping(path = "/incomplete-courses/picture")
  public ResponseEntity<?> deleteCoursePictureUrl(@RequestParam(name = "id") Long courseId, Principal principal) {
    try {
      instructorService.deleteCoursePictureUrl(courseId, principal.getName());
      return ResponseEntity.ok().build();
    } catch (Exception exception) {
      return ResponseEntity.notFound().build();
    }
  }



  public InstructorController(InstructorService instructorService, PublicObjectStorageService publicObjectStorageService, PrivateObjectStorageService privateObjectStorageService, OmiseConfigProperties omiseKey) {
    this.instructorService = instructorService;
    this.publicObjectStorageService = publicObjectStorageService;
    this.privateObjectStorageService = privateObjectStorageService;
    this.omiseKey = omiseKey;
  }
}

package com.ved.backend.service;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.configuration.RoleProperties;
import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.exception.baseException.NotFoundException;
import com.ved.backend.model.*;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.response.FullPendingCourseInfoDto;
import com.ved.backend.response.InstructorInfoDto;
import com.ved.backend.response.PendingCourseResponse;
import com.ved.backend.response.VideoResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminService {

  private final AppUserRepo appUserRepo;
  private final AppRoleRepo appRoleRepo;
  private final CourseRepo courseRepo;
  private final CourseStateProperties courseStateProperties;
  private final RoleProperties roleProperties;
  private final CourseStateService courseStateService;
  private final CourseService courseService;
  private final PrivateObjectStorageService privateObjectStorageService;
  private final PasswordEncoder passwordEncoder;

  private static final Logger log = LoggerFactory.getLogger(AdminService.class);

  // Only use this service for integration testing
  public void registerAdmin(AppUser appUser) {
    if (appUserRepo.existsByUsername(appUser.getUsername())) {
      String usernameAlreadyExist = "User with username: " + appUser.getUsername() + " already exists";
      throw new ConflictException(usernameAlreadyExist);
    }

    log.info("Register new admin: {} to the database", appUser.getUsername());
    appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
    AppRole adminRole = appRoleRepo.findByName(roleProperties.getAdmin());
    appUser.getAppRoles().add(adminRole);
    appUserRepo.save(appUser);
  }

  public List<PendingCourseResponse> getPendingCourses(String username) {
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());
    log.info("Finding all pending courses, admin: {}", username);
    List<Course> pendingCourses = courseRepo.findCoursesByCourseState(pendingState);
    return pendingCourses
        .stream()
        .map(p -> PendingCourseResponse.builder()
            .id(p.getId())
            .name(p.getName())
            .instructorName(p.getInstructor().getStudent().getFullName())
            .build())
        .collect(Collectors.toList());
  }

  public FullPendingCourseInfoDto getPendingCourse(Long courseId, String username) {
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());
    log.info("Finding a pending course, admin: {}", username);
    Course pendingCourse = courseService.getByIdAndCourseState(courseId, pendingState);
    Student student = pendingCourse.getInstructor().getStudent();
    InstructorInfoDto instructorInfoDto = InstructorInfoDto.builder()
        .firstName(student.getFirstName())
        .lastName(student.getLastName())
        .biography(student.getBiography())
        .occupation(student.getOccupation())
        .profilePicUri(student.getProfilePicUri())
        .build();
    return FullPendingCourseInfoDto.builder()
        .name(pendingCourse.getName())
        .price(pendingCourse.getPrice())
        .pictureUrl(pendingCourse.getPictureUrl())
        .instructorInfo(instructorInfoDto)
        .category(pendingCourse.getCategory().getName())
        .requirement(pendingCourse.getRequirement())
        .overview(pendingCourse.getOverview())
        .chapters(pendingCourse.getChapters())
        .build();
  }

  public VideoResponse getVideoUrlFromPendingCourse(Long courseId,
                                                    Integer chapterIndex,
                                                    Integer sectionIndex,
                                                    String username) {
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());
    Course pendingCourse = courseService.getByIdAndCourseState(courseId, pendingState);
    try {
      String videoFileName = String.valueOf(pendingCourse.getChapters()
          .get(chapterIndex)
          .getSections()
          .get(sectionIndex)
          .get("videoUri"));
      String videoUrl = privateObjectStorageService.readFile(videoFileName, username);
      return VideoResponse.builder()
          .videoUrl(videoUrl)
          .build();
    } catch (Exception e) {
      throw new NotFoundException("Video not found");
    }
  }

  public Map<String, String> getHandoutUrlFromPendingCourse(Long courseId,
                                                            Integer chapterIndex,
                                                            Integer sectionIndex,
                                                            String requestHandoutFileName,
                                                            String username) {
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());
    Course pendingCourse = courseService.getByIdAndCourseState(courseId, pendingState);

    try {
      @SuppressWarnings("unchecked")
      List<Map<String, String>> handouts = (List<Map<String, String>>) pendingCourse
          .getChapters()
          .get(chapterIndex)
          .getSections()
          .get(sectionIndex)
          .get("handouts");
      boolean found = handouts.stream().anyMatch(handout -> handout.get("handoutUri").equals(requestHandoutFileName));
      if (!found) {
        throw new NotFoundException("Handout not found");
      }
      String handoutUrl = privateObjectStorageService.readFile(requestHandoutFileName, username);
      return Map.of("handoutUrl", handoutUrl);
    } catch (Exception e) {
      throw new NotFoundException("Handout not found");
    }

  }

  public void changePendingCourseState(Long courseId, Boolean isApproved, String username) {
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());
    Course pendingCourse = courseService.getByIdAndCourseState(courseId, pendingState);
    CourseState newCourseState;
    if (isApproved) {
      log.info("Admin: {} approved course_id: {}", username, courseId);
      newCourseState = courseStateService.getByName(courseStateProperties.getApproved());
    } else {
      log.info("Admin: {} rejected course_id: {}", username, courseId);
      newCourseState = courseStateService.getByName(courseStateProperties.getRejected());
    }
    pendingCourse.setCourseState(newCourseState);
    courseRepo.save(pendingCourse);
  }
}

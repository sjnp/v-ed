package com.ved.backend.service;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.exception.baseException.NotFoundException;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Student;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.response.FullPendingCourseInfoDto;
import com.ved.backend.response.InstructorInfoDto;
import com.ved.backend.response.PendingCourseResponse;
import com.ved.backend.response.VideoResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AdminService {

  private final CourseRepo courseRepo;
  private final CourseStateProperties courseStateProperties;
  private final CourseStateService courseStateService;
  private final CourseService courseService;
  private final PrivateObjectStorageService privateObjectStorageService;

  private static final Logger log = LoggerFactory.getLogger(AdminService.class);

  public List<PendingCourseResponse> getPendingCourses() {
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());
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

  public FullPendingCourseInfoDto getPendingCourse(Long courseId) {
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());
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
    String videoFileName = String.valueOf(pendingCourse.getChapters()
        .get(chapterIndex)
        .getSections()
        .get(sectionIndex)
        .get("videoUri"));
    String videoUrl = privateObjectStorageService.readFile(videoFileName, username);
    return VideoResponse.builder()
        .videoUrl(videoUrl)
        .build();
  }

  public Map<String, String> getHandoutUrlFromPendingCourse(Long courseId,
                                                            Integer chapterIndex,
                                                            Integer sectionIndex,
                                                            String requestHandoutFileName,
                                                            String username) {
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());
    Course pendingCourse = courseService.getByIdAndCourseState(courseId, pendingState);

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
  }

  public void changePendingCourseState(Long courseId, Boolean isApproved) {
    CourseState pendingState = courseStateService.getByName(courseStateProperties.getPending());
    Course pendingCourse = courseService.getByIdAndCourseState(courseId, pendingState);
    CourseState newCourseState;
    if (isApproved) {
      newCourseState = courseStateService.getByName(courseStateProperties.getApproved());
    } else {
      newCourseState = courseStateService.getByName(courseStateProperties.getRejected());
    }
    pendingCourse.setCourseState(newCourseState);
    courseRepo.save(pendingCourse);
  }
}

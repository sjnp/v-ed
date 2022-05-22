package com.ved.backend.service;

import com.ved.backend.exception.tempException.MyException;
import com.ved.backend.exception.CourseNotFoundException;
import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.model.Category;
import com.ved.backend.model.Course;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Student;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.response.AboutCourseResponse;
import com.ved.backend.response.CourseResponse;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepo courseRepo;
    private final CourseStateRepo courseStateRepo;
    private final CategoryRepo categoryRepo;
    private final PrivateObjectStorageService privateObjectStorageService;

    private static final Logger log = LoggerFactory.getLogger(CourseService.class);

    public Course getById(Long courseId) {
        log.info("Get course by id {}", courseId);
        return courseRepo.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    public List<Course> getByCategoryAndCourseState(Category category, CourseState courseState) {
        log.info("Get course by published course satate and category {}", category.getName());
        return courseRepo.findCourseByCategoryAndCourseState(category, courseState);
    }

    public Course getByIdAndPrice(Long courseId, Long price) {
        log.info("Get course id {} and price ", courseId, price);
        return courseRepo.findByIdAndPrice(courseId, price)
            .orElseThrow(() -> new BadRequestException("Course id " + courseId + " not free"));
    } 

    public CourseResponse getCourse(Long courseId) {
        Course course = courseRepo.getById(courseId);

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setCourseId(course.getId());
        courseResponse.setContent(course.getChapters());

        return courseResponse;
    }

    public AboutCourseResponse getAboutCourse(Long courseId) {

        Optional<Course> courseOptional = courseRepo.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new MyException("course.id.not.found", HttpStatus.NOT_FOUND);
        }

        Course course = courseOptional.get();
        AboutCourseResponse response = new AboutCourseResponse(course);

        return response;
    }


    public List<Map<String, Object>> getPendingCourses() {
        CourseState pendingState = courseStateRepo.findByName("PENDING");
        List<Course> pendingCourses = courseRepo.findCoursesByCourseState(pendingState);
        List<Map<String, Object>> pendingCoursesJson = new ArrayList<>();
        for (Course course : pendingCourses) {
            Map<String, Object> courseJson = new HashMap<>();
            courseJson.put("id", course.getId());
            courseJson.put("name", course.getName());
            Student student = course.getInstructor().getStudent();
            courseJson.put("instructorName", student.getFirstName() + " " + student.getLastName());
            pendingCoursesJson.add(courseJson);
        }
        return pendingCoursesJson;
    }

    public Map<String, Object> getPendingCourse(Long courseId) {
        CourseState pendingState = courseStateRepo.findByName("PENDING");
        try {
            Course course = courseRepo.findCourseByCourseStateAndId(pendingState, courseId);
            Map<String, Object> courseJson = new HashMap<>();
            courseJson.put("name", course.getName());
            courseJson.put("price", course.getPrice());
            courseJson.put("pictureUrl", course.getPictureUrl());
            Map<String, Object> instructorInfo = new HashMap<>();
            Student student = course.getInstructor().getStudent();
            instructorInfo.put("firstName", student.getFirstName());
            instructorInfo.put("lastName", student.getLastName());
            instructorInfo.put("biography", student.getBiography());
            instructorInfo.put("occupation", student.getOccupation());
            instructorInfo.put("profilePicUri", student.getProfilePicUri());
            courseJson.put("instructorInfo", instructorInfo);
            courseJson.put("category", course.getCategory().getName());
            courseJson.put("requirement", course.getRequirement());
            courseJson.put("overview", course.getOverview());
            courseJson.put("chapters", course.getChapters());
            return courseJson;
        } catch (Exception exception) {
            throw new RuntimeException("Course not found");
        }
    }

    public String getVideoUriFromPendingCourse(Long courseId,
                                               Integer chapterIndex,
                                               Integer sectionIndex,
                                               String username) {
        try {
            CourseState pendingState = courseStateRepo.findByName("PENDING");
            String courseVideoUri = String.valueOf(
                courseRepo.findCourseByCourseStateAndId(pendingState, courseId)
                    .getChapters()
                    .get(chapterIndex)
                    .getSections()
                    .get(sectionIndex)
                    .get("videoUri"));
            return privateObjectStorageService.createParToReadFile(courseVideoUri, username);
//       TODO: Need to add orElseThrow when calling courseRepo
//      if (courseVideoUri.equals(requestVideoUri)) {
//        return privateObjectStorageService.createParToReadFile(requestVideoUri, username);
//      } else {
//        throw new RuntimeException("Course not found");
//      }
        } catch (Exception exception) {
            throw new RuntimeException("Course not found");
        }
    }

    public String getHandoutUrlFromPendingCourse(Long courseId, Integer chapterIndex, Integer sectionIndex, String requestHandoutUri, String username) {
        try {
            CourseState pendingState = courseStateRepo.findByName("PENDING");

            @SuppressWarnings("unchecked")
            ArrayList<LinkedHashMap<String, String>> handouts = (ArrayList<LinkedHashMap<String, String>>) courseRepo.findCourseByCourseStateAndId(pendingState, courseId)
                .getChapters()
                .get(chapterIndex)
                .getSections()
                .get(sectionIndex)
                .get("handouts");
            if (handouts.stream().anyMatch(handout -> handout.get("handoutUri").equals(requestHandoutUri))) {
                return privateObjectStorageService.createParToReadFile(requestHandoutUri, username);
            } else {
                throw new RuntimeException("Course not found");
            }
        } catch (Exception exception) {
            throw new RuntimeException("Course not found");
        }
    }

    public void changePendingCourseState(Long courseId, Boolean isApproved) {
        try {
            CourseState pendingState = courseStateRepo.findByName("PENDING");
            Course course = courseRepo.findCourseByCourseStateAndId(pendingState, courseId);
            CourseState newCourseState;
            if (isApproved) {
                newCourseState = courseStateRepo.findByName("APPROVED");
            } else {
                newCourseState = courseStateRepo.findByName("REJECTED");
            }
            course.setCourseState(newCourseState);
            courseRepo.save(course);
        } catch (Exception exception) {
            throw new RuntimeException("Course not found");
        }
    }

    public Collection<CourseStateRepo.IdAndNameOnly> getAllCourseStates() {
//        log.info("Getting all course states");
        return courseStateRepo.findAllBy();
    }

    public Collection<CategoryRepo.IdAndNameOnly> getAllCategories() {
//        log.info("Getting all categories");
        return categoryRepo.findAllBy();
    }

}
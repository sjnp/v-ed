package com.ved.backend.service;

import com.ved.backend.model.AppUser;
import com.ved.backend.model.Course;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import com.ved.backend.repo.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

  private final AppUserRepo appUserRepo;
  private final CourseRepo courseRepo;
  private final CourseStateRepo courseStateRepo;
  private final InstructorRepo instructorRepo;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorServiceImpl.class);

  @Override
  public Long createCourse(Course course, String username) {
    log.info("Creating class from instructor: {}", username);
    Instructor instructor = appUserRepo.findByUsername(username).getStudent().getInstructor();
    course.setCourseState(courseStateRepo.findByName("Incomplete"));
    course.setInstructor(instructor);
    instructor.getCourses().add(course);
    courseRepo.save(course);
    instructorRepo.save(instructor);
    return course.getId();
  }

  public InstructorServiceImpl(AppUserRepo appUserRepo, CourseRepo courseRepo, CourseStateRepo courseStateRepo, InstructorRepo instructorRepo) {
    this.appUserRepo = appUserRepo;
    this.courseRepo = courseRepo;
    this.courseStateRepo = courseStateRepo;
    this.instructorRepo = instructorRepo;
  }
}

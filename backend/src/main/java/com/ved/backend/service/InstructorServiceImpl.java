package com.ved.backend.service;

import com.ved.backend.model.Course;
import com.ved.backend.model.Instructor;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.repo.CourseStateRepo;
import com.ved.backend.repo.InstructorRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

  private final AppUserRepo appUserRepo;
  private final CategoryRepo categoryRepo;
  private final CourseStateRepo courseStateRepo;
  private final InstructorRepo instructorRepo;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorServiceImpl.class);

  @Override
  public void createCourse(Course course, String username) {
    log.info("Creating class from instructor: {}", username);
    Instructor instructor = appUserRepo.findByUsername(username).getStudent().getInstructor();
    course.setCourseState(courseStateRepo.findByName("Incomplete"));
    instructor.getCourses().add(course);
    instructorRepo.save(instructor);
  }

  public InstructorServiceImpl(AppUserRepo appUserRepo, CategoryRepo categoryRepo, CourseStateRepo courseStateRepo, InstructorRepo instructorRepo) {
    this.appUserRepo = appUserRepo;
    this.categoryRepo = categoryRepo;
    this.courseStateRepo = courseStateRepo;
    this.instructorRepo = instructorRepo;
  }
}

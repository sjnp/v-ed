package com.ved.backend.service;

import com.ved.backend.repo.CourseStateRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class CourseStateServiceImpl implements CourseStateService {

  private final CourseStateRepo courseStateRepo;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorServiceImpl.class);


  @Override
  public Collection<CourseStateRepo.IdAndNameOnly> getAllCourseStates() {
    log.info("Getting all course states");
    return courseStateRepo.findAllBy();
  }

  public CourseStateServiceImpl(CourseStateRepo courseStateRepo) {
    this.courseStateRepo = courseStateRepo;
  }

}

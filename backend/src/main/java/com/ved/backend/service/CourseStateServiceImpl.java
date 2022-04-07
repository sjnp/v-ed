package com.ved.backend.service;

import com.ved.backend.repo.CourseStateRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class CourseStateServiceImpl implements CourseStateService {

  private final CourseStateRepo courseStateRepo;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorServiceImpl.class);


  @Override
  public List<CourseStateRepo.IdAndNameOnly> getAllCourseState() {
    log.info("Getting all course states");
    return (List<CourseStateRepo.IdAndNameOnly>) new ArrayList(courseStateRepo.findAllBy());
  }

  public CourseStateServiceImpl(CourseStateRepo courseStateRepo) {
    this.courseStateRepo = courseStateRepo;
  }

}

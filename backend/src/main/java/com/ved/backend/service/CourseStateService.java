package com.ved.backend.service;

import com.ved.backend.repo.CourseStateRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@AllArgsConstructor
@Service
@Transactional
public class CourseStateService {
  private final CourseStateRepo courseStateRepo;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CourseStateService.class);

  public Collection<CourseStateRepo.IdAndNameOnly> getAllCourseStates() {
    log.info("Getting all course states");
    return courseStateRepo.findAllBy();
  }
}

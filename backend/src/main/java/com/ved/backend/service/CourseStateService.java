package com.ved.backend.service;

import com.ved.backend.repo.CourseStateRepo;

import java.util.Collection;

public interface CourseStateService {
  Collection<CourseStateRepo.IdAndNameOnly> getAllCourseStates();
}

package com.ved.backend.service;

import java.util.List;

import com.ved.backend.exception.NotFoundException;
import com.ved.backend.model.CourseState;
import com.ved.backend.repo.CourseStateRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CourseStateService {
    
    private final CourseStateRepo courseStateRepo;

    private static final Logger log = LoggerFactory.getLogger(CourseStateService.class);

    public CourseState getByName(String name) {
        log.info("Get course state by name {}", name);
        return courseStateRepo.findCourseStateByName(name).orElseThrow(() -> NotFoundException.courseState(name));
    }

    public List<CourseState> getAll() {
        log.info("Get all course state");
        return courseStateRepo.findAll();
    }

}

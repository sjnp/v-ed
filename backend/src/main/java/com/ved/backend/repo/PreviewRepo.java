package com.ved.backend.repo;

import java.util.ArrayList;

import com.ved.backend.model.Course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PreviewRepo extends JpaRepository<Course, Long> {
 
    public ArrayList<Course> findAll();

}
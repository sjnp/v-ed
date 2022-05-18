package com.ved.backend.controller;

import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {

  private final CourseService courseService;

  @GetMapping("")
  public Collection<CategoryRepo.IdAndNameOnly> getAllCategories() {
    return courseService.getAllCategories();
  }

}

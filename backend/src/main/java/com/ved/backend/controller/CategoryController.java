package com.ved.backend.controller;

import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("")
  public Collection<CategoryRepo.IdAndNameOnly> getAllCategories() {
    return categoryService.getAllCategories();
  }

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }
}

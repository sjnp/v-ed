package com.ved.backend.service;

import com.ved.backend.repo.CategoryRepo;

import java.util.Collection;

public interface CategoryService {
  Collection<CategoryRepo.IdAndNameOnly> getAllCategories();
}

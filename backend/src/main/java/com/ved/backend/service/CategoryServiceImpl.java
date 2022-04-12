package com.ved.backend.service;

import com.ved.backend.repo.CategoryRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepo categoryRepo;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorServiceImpl.class);

  @Override
  public Collection<CategoryRepo.IdAndNameOnly> getAllCategories() {
    log.info("Getting all categories");
    return categoryRepo.findAllBy();
  }

  public CategoryServiceImpl(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }
}

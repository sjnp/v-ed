package com.ved.backend.service;

import com.ved.backend.repo.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@AllArgsConstructor
@Service
@Transactional
public class CategoryService {
  private final CategoryRepo categoryRepo;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CategoryService.class);

  public Collection<CategoryRepo.IdAndNameOnly> getAllCategories() {
    log.info("Getting all categories");
    return categoryRepo.findAllBy();
  }

}

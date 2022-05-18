package com.ved.backend.service;

import java.util.List;

import com.ved.backend.exception.CategoryNotFoundException;
import com.ved.backend.model.Category;
import com.ved.backend.repo.CategoryRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    public Category getByName(String name) {
        log.info("Get category by name {}", name);
        return categoryRepo.findByName(name).orElseThrow(() -> new CategoryNotFoundException(name));
    }

    public List<Category> getAll() {
        log.info("Get all category");
        return categoryRepo.findAll();
    }
}
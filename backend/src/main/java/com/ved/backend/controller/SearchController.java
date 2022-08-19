package com.ved.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ved.backend.response.CourseCardResponse;
import com.ved.backend.service.SearchService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/search")
public class SearchController {
    
    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<CourseCardResponse>> search(
        @RequestParam(name = "name", required = false) String courseName,
        @RequestParam(name = "category", required = false) String categoryName,
        @RequestParam(name = "min_price", required = false) Long minPrice,
        @RequestParam(name = "max_price", required = false) Long maxPrice,
        @RequestParam(name = "rating", required = false) String rating
    ) {
        List<CourseCardResponse> response = searchService.search(courseName, categoryName, minPrice, maxPrice, rating);
        return ResponseEntity.ok().body(response);
    }

}
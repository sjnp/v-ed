package com.ved.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ved.backend.service.SearchService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/search")
public class SearchController {
    
    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<String> search() {
        String response = searchService.search();
        return ResponseEntity.ok().body(response);
    }

}
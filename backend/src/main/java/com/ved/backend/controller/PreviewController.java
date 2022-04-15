package com.ved.backend.controller;

import java.util.ArrayList;

import com.ved.backend.response.PreviewResponse;
import com.ved.backend.service.PreviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/preview")
public class PreviewController {

    @Autowired
    private PreviewService previewService;
 
    @GetMapping("/{category}")
    public ResponseEntity<ArrayList<PreviewResponse>> getCategory(@PathVariable String category) {

        ArrayList<PreviewResponse> previewResponseList = previewService.getPreviewCategory(category);
        return ResponseEntity.ok().body(previewResponseList);
    }

    @GetMapping("/my-course")
    public ResponseEntity<ArrayList<PreviewResponse>> getMyCourse() {
        
        ArrayList<PreviewResponse> previewResponseList = previewService.getPreviewMyCourse();
        return ResponseEntity.ok().body(previewResponseList);
    }

}
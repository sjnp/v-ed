package com.ved.backend.controller;

import com.ved.backend.response.PreviewResponse;
import com.ved.backend.service.PreviewService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/preview")
public class PreviewController {

    private final PreviewService previewService;

    @GetMapping("/category")
    public ResponseEntity<PreviewResponse> getCategory() {

        PreviewResponse previewResponse = previewService.getPreviewCategory();
        return ResponseEntity.ok().body(previewResponse);
    }

    @GetMapping("/my-course")
    public ResponseEntity<PreviewResponse> getMyCourse() {
        
        PreviewResponse previewResponse = previewService.getPreviewMyCourse();
        return ResponseEntity.ok().body(previewResponse);
    }

    public PreviewController(PreviewService previewService) {
        this.previewService = previewService;
    }

}
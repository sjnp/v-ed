package com.ved.backend.service;

import java.util.ArrayList;

import com.ved.backend.response.PreviewResponse;

public interface PreviewService {
    
    public ArrayList<PreviewResponse> getPreviewCategory(String categoryName);

    public ArrayList<PreviewResponse> getPreviewMyCourse();

}
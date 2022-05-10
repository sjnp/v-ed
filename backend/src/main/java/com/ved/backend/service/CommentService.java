package com.ved.backend.service;

import com.ved.backend.response.CommentResponse;

public interface CommentService {
    
    public CommentResponse create(Long questionBoardId, String comment, String username);

}
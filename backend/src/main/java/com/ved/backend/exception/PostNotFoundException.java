package com.ved.backend.exception;

import com.ved.backend.exception.baseException.NotFoundException;

public class PostNotFoundException extends NotFoundException {
 
    public PostNotFoundException(Long postId) {
        super("Post id " + postId + " not found");
    }

}
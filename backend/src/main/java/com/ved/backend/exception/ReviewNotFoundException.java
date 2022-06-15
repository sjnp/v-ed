package com.ved.backend.exception;

import com.ved.backend.exception.baseException.NotFoundException;

public class ReviewNotFoundException extends NotFoundException{
 
    public ReviewNotFoundException(Long reviewId) {
        super("Review id " + reviewId + " not found");
    }
    
}
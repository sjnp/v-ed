package com.ved.backend.exception;

import com.ved.backend.exception.baseException.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException(Long commentId) {
        super("Comment id " + commentId + " not found");
    }
    
}
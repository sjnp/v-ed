package com.ved.backend.exception;

import com.ved.backend.exception.baseException.NotFoundException;

public class AnswerNotFoundException extends NotFoundException {

    public AnswerNotFoundException(Long answerId) {
        super("Answer id " + answerId + " not found");
    }

}
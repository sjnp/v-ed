package com.ved.backend.controller;

import com.ved.backend.exception.RegisterException;
import com.ved.backend.response.ErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorAdvicer {
    
    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<ErrorResponse> handleRegisterException(RegisterException error) {
        return ResponseEntity.status(error.getHttpStatus()).body(error.getErrorResponse());
    }

}
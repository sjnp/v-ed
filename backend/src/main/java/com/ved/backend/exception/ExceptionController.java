package com.ved.backend.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @Autowired
    private ExceptionService exceptionService;
    
    @ExceptionHandler(MyException.class)
    public ResponseEntity<ResponseException> handleException(MyException ex) {
        
        return exceptionService.responseError(ex);
    }
}
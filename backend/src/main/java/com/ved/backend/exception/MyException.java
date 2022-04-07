package com.ved.backend.exception;

import org.springframework.http.HttpStatus;

public class MyException extends RuntimeException {

    private HttpStatus status;

    public MyException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    
}
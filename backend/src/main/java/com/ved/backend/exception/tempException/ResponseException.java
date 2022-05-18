package com.ved.backend.exception.tempException;

import org.springframework.http.HttpStatus;

public class ResponseException {
 
    private String error;
    private HttpStatus status;

    public ResponseException(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status.value();
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
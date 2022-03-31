package com.ved.backend.exception;

import org.springframework.http.HttpStatus;
import com.ved.backend.response.ErrorResponse;

public class BaseException extends Exception {

    // private int status;
    private String error;
    private HttpStatus status;

    public BaseException(HttpStatus status, String error) {
        super();
        this.status = status;
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.status.value(), this.error);
    }
    
    public HttpStatus getHttpStatus() {
        return this.status;
    }
}
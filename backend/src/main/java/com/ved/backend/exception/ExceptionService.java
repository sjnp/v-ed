package com.ved.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExceptionService {
    
    public ResponseEntity<ResponseException> responseError(MyException ex) {
        ResponseException apiError = new ResponseException(ex.getMessage(), ex.getStatus());
        return ResponseEntity.status(ex.getStatus()).body(apiError);
    }
}

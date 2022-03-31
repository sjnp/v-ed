package com.ved.backend.exception;

import org.springframework.http.HttpStatus;

public class RegisterException extends BaseException {

    // public RegisterException(int status, String error) {
    //     super(status, "register." + error);
    // }

    public RegisterException(HttpStatus status, String error) {
        super(status, "register." + error);
    }

    public static RegisterException emailDuplicate() {
        return new RegisterException(HttpStatus.CONFLICT, "email.duplicate");
    }
    
}
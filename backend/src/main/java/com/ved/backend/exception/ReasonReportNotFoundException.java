package com.ved.backend.exception;

import com.ved.backend.exception.baseException.NotFoundException;

public class ReasonReportNotFoundException extends NotFoundException {

    public ReasonReportNotFoundException(Long reasonReportId) {
        super("Reason report id " + reasonReportId + " not found");
    }
    
}
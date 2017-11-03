package com.ionut.ciuta.sci1hw.exception;

import org.springframework.http.HttpStatus;

public class ResourceInConflict extends ExceptionWithStatusCode {
    public ResourceInConflict() {
        super(HttpStatus.CONFLICT, "Already Existing");
    }
}

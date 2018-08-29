package com.ionut.ciuta.posd1.exception;

import org.springframework.http.HttpStatus;

public class ResourceInConflict extends ExceptionWithStatusCode {
    public ResourceInConflict() {
        super(HttpStatus.CONFLICT, "Already Existing");
    }
}

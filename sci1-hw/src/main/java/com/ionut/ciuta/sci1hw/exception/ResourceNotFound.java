package com.ionut.ciuta.sci1hw.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends ExceptionWithStatusCode {
    public ResourceNotFound() {
        super(HttpStatus.NOT_FOUND, "Not Existing");
    }
}
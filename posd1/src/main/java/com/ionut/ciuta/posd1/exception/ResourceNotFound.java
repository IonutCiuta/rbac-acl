package com.ionut.ciuta.posd1.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends ExceptionWithStatusCode {
    public ResourceNotFound() {
        super(HttpStatus.NOT_FOUND, "Not Existing");
    }
}
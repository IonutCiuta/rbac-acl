package com.ionut.ciuta.sci1hw.exception;

import org.springframework.http.HttpStatus;

public abstract class ExceptionWithStatusCode extends RuntimeException {
    public HttpStatus status;

    public ExceptionWithStatusCode(HttpStatus status) {
        this.status = status;
    }
}

package com.ionut.ciuta.sci1hw.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends RuntimeException {
    public static final HttpStatus status = HttpStatus.NOT_FOUND;
}

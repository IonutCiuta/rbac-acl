package com.ionut.ciuta.sci1hw.exception;

import org.springframework.http.HttpStatus;

public class ResourceInConflict extends Exception{
    public static final HttpStatus status = HttpStatus.CONFLICT;
}

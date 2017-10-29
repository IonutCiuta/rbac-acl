package com.ionut.ciuta.sci1hw.exception;

import org.springframework.http.HttpStatus;

public class ResourceOpertaionNotPermitted extends RuntimeException {
    public static final HttpStatus status = HttpStatus.FORBIDDEN;
}

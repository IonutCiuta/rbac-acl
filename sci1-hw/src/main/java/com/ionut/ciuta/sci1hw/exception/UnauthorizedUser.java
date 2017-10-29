package com.ionut.ciuta.sci1hw.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedUser extends RuntimeException {
    public static final HttpStatus status = HttpStatus.UNAUTHORIZED;
}

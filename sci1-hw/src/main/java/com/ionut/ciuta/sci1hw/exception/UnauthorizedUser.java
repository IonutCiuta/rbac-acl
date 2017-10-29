package com.ionut.ciuta.sci1hw.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedUser extends ExceptionWithStatusCode {
    public UnauthorizedUser() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
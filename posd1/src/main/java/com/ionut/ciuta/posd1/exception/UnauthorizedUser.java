package com.ionut.ciuta.posd1.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedUser extends ExceptionWithStatusCode {
    public UnauthorizedUser() {
        super(HttpStatus.UNAUTHORIZED, "Nonexisting User");
    }
}
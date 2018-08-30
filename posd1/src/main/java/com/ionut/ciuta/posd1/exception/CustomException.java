package com.ionut.ciuta.posd1.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends ExceptionWithStatusCode {
    public CustomException(HttpStatus status, String text) {
        super(status, text);
    }
}

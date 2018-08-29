package com.ionut.ciuta.posd1.exception;

import org.springframework.http.HttpStatus;

public class ResourceOperationNotPermitted extends ExceptionWithStatusCode {
    public ResourceOperationNotPermitted() {
        super(HttpStatus.FORBIDDEN, "Not Authorized");
    }
}

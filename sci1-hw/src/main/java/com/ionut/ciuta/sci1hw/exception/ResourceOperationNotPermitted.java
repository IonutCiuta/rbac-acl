package com.ionut.ciuta.sci1hw.exception;

import org.springframework.http.HttpStatus;

public class ResourceOperationNotPermitted extends ExceptionWithStatusCode {
    public ResourceOperationNotPermitted() {
        super(HttpStatus.FORBIDDEN, "Not Authorized");
    }
}

package com.lastminute.recruitment.domain.error;

public abstract class ValidationException extends RuntimeException {

    public ValidationException(String cause) {
        super(cause);
    }
}

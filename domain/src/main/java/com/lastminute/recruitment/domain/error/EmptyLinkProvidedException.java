package com.lastminute.recruitment.domain.error;

public class EmptyLinkProvidedException extends ValidationException{

    private final static String MESSAGE = "Provided link cannot be empty or null.";

    public EmptyLinkProvidedException() {
        super(MESSAGE);
    }
}

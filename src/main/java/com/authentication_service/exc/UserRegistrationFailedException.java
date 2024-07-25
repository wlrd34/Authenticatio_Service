package com.authentication_service.exc;

public class UserRegistrationFailedException extends RuntimeException {
    public UserRegistrationFailedException(String message) {
        super(message);
    }
}

package com.katsshura.demo.junit.core.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "User with {Id = %s} could not be found. Make sure the ID exists!";
    private final Long userId;

    public UserNotFoundException(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return String.format(MESSAGE, this.userId);
    }
}

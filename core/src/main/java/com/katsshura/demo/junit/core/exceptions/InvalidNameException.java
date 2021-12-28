package com.katsshura.demo.junit.core.exceptions;

public class InvalidNameException extends RuntimeException {
    private static final String MESSAGE = "The name {%s} informed is invalid. We expected a name composed by first and last name";
    private final String name;

    public InvalidNameException(String name) {
        this.name = name;
    }

    public String getMessage() {
        return String.format(MESSAGE, this.name);
    }

}

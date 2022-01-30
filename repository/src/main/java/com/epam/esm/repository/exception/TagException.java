package com.epam.esm.repository.exception;

public class TagException extends RuntimeException {
    public TagException() {
        super();
    }

    public TagException(String message) {
        super(message);
    }

    public TagException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagException(Throwable cause) {
        super(cause);
    }
}

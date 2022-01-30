package com.epam.esm.service.exception;

import java.util.function.Supplier;

public class IncorrectParameterException extends RuntimeException {

    public IncorrectParameterException(String message) {
        super(message);
    }

    public static Supplier<IncorrectParameterException> incorrect(String message) {
        return () -> new IncorrectParameterException(message);
    }
}

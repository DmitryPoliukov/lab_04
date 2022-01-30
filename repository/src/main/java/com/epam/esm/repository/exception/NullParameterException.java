package com.epam.esm.repository.exception;

import java.util.function.Supplier;

public class NullParameterException extends RuntimeException {

    public NullParameterException(String message) {
        super(message);
    }

    public static Supplier<NullParameterException> incorrect(String message) {
        return () -> new NullParameterException(message);
    }
}

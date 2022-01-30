package com.epam.esm.service.exception;

public class ResourceValidationException extends ResourceException{

    public ResourceValidationException(String message, int resourceId) {
        super(message, resourceId);
    }
}

package com.epam.esm.service.exception;

public class ResourceNotFoundException extends ResourceException {

    public ResourceNotFoundException(String message, int resourceId) {
        super(message, resourceId);
    }
}

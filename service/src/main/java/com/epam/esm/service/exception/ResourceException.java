package com.epam.esm.service.exception;

import java.util.function.Supplier;

public class ResourceException extends RuntimeException {
    private final int resourceId;

    public int getResourceId() {
        return resourceId;
    }

    public static Supplier<ResourceException> notFoundWithCertificateId(int id) {
        String message = String.format("There is no certificate with id = %s", id);
        return () -> new ResourceNotFoundException(message, id);
    }

    public static Supplier<ResourceException> validationWithCertificateId(int id) {
        String message = String.format("There is no certificate with id = %s", id);
        return () -> new ResourceValidationException(message, id);
    }

    public static Supplier<ResourceException> notFoundWithTagId(int id) {
        String message = String.format("There is no tag with id = %s", id);
        return () -> new ResourceNotFoundException(message, id);
    }

    public static Supplier<ResourceException> validationWithTagId(int id) {
        String message = String.format("There is no tag with id = %s", id);
        return () -> new ResourceValidationException(message, id);
    }

    public static Supplier<ResourceException> notFoundWithUser(int id) {
        String message = String.format("There is no user with id = %s", id);
        return () -> new ResourceNotFoundException(message, id);
    }

    public static Supplier<ResourceException> validationWithUser(int id) {
        String message = String.format("There is no user with id = %s", id);
        return () -> new ResourceValidationException(message, id);
    }

    public static Supplier<ResourceException> notFoundWithOrder(int id) {
        String message = String.format("There is no order with id = %s", id);
        return () -> new ResourceNotFoundException(message, id);
    }

    public static Supplier<ResourceException> validationWithOrder(int id) {
        String message = String.format("There is no order with id = %s", id);
        return () -> new ResourceValidationException(message, id);
    }


    public ResourceException(String message, int resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

}

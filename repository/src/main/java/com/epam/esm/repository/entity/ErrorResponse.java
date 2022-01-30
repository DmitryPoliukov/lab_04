package com.epam.esm.repository.entity;

public class ErrorResponse {
    private final String errorMessage;
    private final String errorCode;

    public ErrorResponse(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

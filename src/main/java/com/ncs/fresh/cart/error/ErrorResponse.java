package com.ncs.fresh.cart.error;

public class ErrorResponse {
    private final String error;
    private final String message;

    public ErrorResponse(String errorType, String message) {
        this.error = errorType;
        this.message = message;

    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

}

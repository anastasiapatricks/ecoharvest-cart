package com.ncs.fresh.cart.model;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private final String message;
    private final int status;
    private final LocalDateTime timestamp;
    private T data; // Payload or data field

    public ApiResponse(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public ApiResponse(String message, HttpStatus status, T data) {
        this.message = message;
        this.status = status.value();
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }


}

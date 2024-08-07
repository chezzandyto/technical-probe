package com.demoBank.core.model;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String message;
    private T data;
    private int code;

    public ApiResponse(String message, T data, int code) {
        this.message = message;
        this.data = data;
        this.code = code;
    }
}

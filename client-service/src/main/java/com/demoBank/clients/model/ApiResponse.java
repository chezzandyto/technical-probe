package com.demoBank.clients.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ApiResponse<T> implements Serializable {
    private String message;
    private T data;
    private int code;

    public ApiResponse(String message, T data, int code) {
        this.message = message;
        this.data = data;
        this.code = code;
    }
}

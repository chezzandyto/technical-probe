package com.demoBank.core.util;

import com.demoBank.core.model.ApiResponse;
import com.demoBank.core.model.ClientErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;

public class ApiResponseUtil implements Serializable {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("Success", data, HttpStatus.OK.value());
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>("Created", data, HttpStatus.CREATED.value());
    }

    public static <T> ApiResponse<T> delete() {
        return new ApiResponse<>("Deleted", null, HttpStatus.OK.value());
    }

    public static ApiResponse<String> badRequest(RuntimeException e) {
        return new ApiResponse<>("Error", e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    public static ApiResponse<String> error(Exception e) {
        return new ApiResponse<>("Error", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}

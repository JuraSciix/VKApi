package com.vk.api.exceptions;

import com.vk.api.objects.ApiError;

public class ApiException extends Exception {

    private final ApiError error;

    public ApiException(ApiError error) {
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return error.code + ": " + error.message;
    }
}

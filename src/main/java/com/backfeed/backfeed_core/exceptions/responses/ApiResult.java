package com.backfeed.backfeed_core.exceptions.responses;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiResult<T>(
    int httpStatus,
    boolean success,
    String message,
    T data,
    LocalDateTime timeStamp) {
    public static <T> ApiResult<T> success(String message, HttpStatus status, T data){
        return new ApiResult<>(status.value(), true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResult<T> error(String message, HttpStatus status){
        return new ApiResult<>(status.value(), false, message, null, LocalDateTime.now());
    }

    public static ApiResult<Void> success(String message, HttpStatus status){
        return new ApiResult<>(status.value(), true, message, null, LocalDateTime.now());
    }
}




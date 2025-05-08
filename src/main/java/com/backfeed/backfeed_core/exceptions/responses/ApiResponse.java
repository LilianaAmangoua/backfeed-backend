package com.backfeed.backfeed_core.exceptions.responses;

public record ApiResponse<T>(
        String errorCode,
        boolean success,
        String message,
        T data
) {}


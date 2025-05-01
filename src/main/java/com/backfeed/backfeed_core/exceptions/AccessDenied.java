package com.backfeed.backfeed_core.exceptions;

public class AccessDenied extends RuntimeException {
    public AccessDenied(String message) {
        super(message);
    }
}

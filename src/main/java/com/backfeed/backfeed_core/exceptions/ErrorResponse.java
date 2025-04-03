package com.backfeed.backfeed_core.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;


@Getter
@Setter
public class ErrorResponse {
    private HttpStatus httpStatus;
    private String message;
    private String errors;

    public ErrorResponse() {
    }

    public ErrorResponse(HttpStatus httpStatus, String message, String errors) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.errors = errors;
    }

}

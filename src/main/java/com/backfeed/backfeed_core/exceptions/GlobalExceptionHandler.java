package com.backfeed.backfeed_core.exceptions;

import com.backfeed.backfeed_core.exceptions.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An internal error has occurred.",
                e.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "This user was not found.",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<ErrorResponse> handleJwtValidationException(JwtValidationException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_ACCEPTABLE,
                "Token is invalid.",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleJwtTokenExpiredException(JwtTokenExpiredException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                "Token is expired. Please login.",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                "Username or password is invalid.",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }


}

package com.backfeed.backfeed_core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erreur 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Une erreur interne est survenue.",
                e.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Erreurs sur USER
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFound ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "Cet utilisateur n'a pas été trouvé.",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<ErrorResponse> handleJwtValidationException(JwtValidationException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_ACCEPTABLE,
                "Le token n'est pas valide.",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleJwtTokenExpiredException(JwtTokenExpiredException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_ACCEPTABLE,
                "Le token est expiré. Veuillez vous reconnecter.",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }


}

package com.backfeed.backfeed_core.exceptions;

import com.backfeed.backfeed_core.exceptions.responses.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResult<Void>> handleUserNotFoundException(UserNotFoundException ex){
        log.error("UserNotFoundException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResult.error("User not found.", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<ApiResult<Void>> handleJwtValidationException(JwtValidationException ex){
        log.error("JwtValidationException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(ApiResult.error("Token is invalid.", HttpStatus.NOT_ACCEPTABLE));
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ApiResult<Void>> handleJwtTokenExpiredException(JwtTokenExpiredException ex){
        log.error("JwtTokenExpiredException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResult.error("Authentication token has expired.", HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResult<Void>> handleBadCredentialsException(BadCredentialsException ex){
        log.error("BadCredentialsException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResult.error("Invalid username or password.", HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResult<Void>> handleRoleNotFoundException(RoleNotFoundException ex){
        log.error("RoleNotFoundException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResult.error("Role not found.", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({MailException.class, MailSendException.class})
    public ResponseEntity<ApiResult<Void>> handleMailNotSentException(Exception ex){
        log.error("Mail sending exception caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResult.error("A mail delivery error occurred.", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(InvitationAlreadyPending.class)
    public ResponseEntity<ApiResult<Void>> handleExistingInvitation(InvitationAlreadyPending ex){
        log.error("InvitationAlreadyPending caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.error("Invitation already sent.", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    public ResponseEntity<ApiResult<Void>> handleInvitationNotFound(InvitationNotFoundException ex){
        log.error("InvitationNotFoundException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResult.error("Invitation not found.", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(InvalidInvitation.class)
    public ResponseEntity<ApiResult<Void>> handleInvalidInvitation(InvalidInvitation ex){
        log.error("InvalidInvitation caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.error("Invitation not valid.", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ApiResult<Void>> handleUserAlreadyExistsException(UserAlreadyExists ex){
        log.error("UserAlreadyExists caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.error("User already exists.", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(AccessDenied.class)
    public ResponseEntity<ApiResult<Void>> handleAccessDeniedException(AccessDenied ex){
        log.error("AccessDenied caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResult.error("Access denied.", HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ApiResult<Void>> handleHttpMessageConversionException(HttpMessageConversionException ex){
        log.error("HttpMessageConversionException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.error("Missing request body.", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(InvitationException.class)
    public ResponseEntity<ApiResult<Void>> handleInvitationException(InvitationException ex) {
        log.error("InvitationException caught: {} ", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResult.error("An error occurred while sending the invitation.", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<Void>> handleMethodNotValidException(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.error("Some fields are invalid. Please correct the errors and try again.", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult<Void>> handleGlobalException(Exception e) {
        log.error("Unhandled exception caught: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResult.error("An internal error has occurred.", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

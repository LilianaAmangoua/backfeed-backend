package com.backfeed.backfeed_core.exceptions;

import com.backfeed.backfeed_core.exceptions.responses.ApiResponse;
import com.backfeed.backfeed_core.exceptions.responses.ErrorResponse;
import com.backfeed.backfeed_core.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(UserNotFoundException ex){
        log.error("UserNotFoundException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("USER_NOT_FOUND", false, "User not found.", null));
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleJwtValidationException(JwtValidationException ex){
        log.error("JwtValidationException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ApiResponse<>("JWT_INVALID", false, "Token is invalid.", null));
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ApiResponse<Void>> handleJwtTokenExpiredException(JwtTokenExpiredException ex){
        log.error("JwtTokenExpiredException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>("JWT_EXPIRED", false, "Authentication token has expired.", null));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentialsException(BadCredentialsException ex){
        log.error("BadCredentialsException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>("BAD_CREDENTIALS", false, "Invalid username or password.", null));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleRoleNotFoundException(RoleNotFoundException ex){
        log.error("RoleNotFoundException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("ROLE_NOT_FOUND", false, "Role not found.", null));
    }

    @ExceptionHandler({MailException.class, MailSendException.class})
    public ResponseEntity<ApiResponse<Void>> handleMailNotSentException(Exception ex){
        log.error("Mail sending exception caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("MAIL_ERROR", false, "A mail delivery error occurred.", null));
    }

    @ExceptionHandler(InvitationAlreadyPending.class)
    public ResponseEntity<ApiResponse<Void>> handleExistingInvitation(InvitationAlreadyPending ex){
        log.error("InvitationAlreadyPending caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>("INVITATION_PENDING", false, "Invitation already sent.", null));
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvitationNotFound(InvitationNotFoundException ex){
        log.error("InvitationNotFoundException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("INVITATION_NOT_FOUND", false, "Invitation not found.", null));
    }

    @ExceptionHandler(InvalidInvitation.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidInvitation(InvalidInvitation ex){
        log.error("InvalidInvitation caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>("INVALID_INVITATION", false, "Invitation not valid.", null));
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExistsException(UserAlreadyExists ex){
        log.error("UserAlreadyExists caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>("USER_EXISTS", false, "User already exists.", null));
    }

    @ExceptionHandler(AccessDenied.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDenied ex){
        log.error("AccessDenied caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>("ACCESS_DENIED", false, "Access denied.", null));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageConversionException(HttpMessageConversionException ex){
        log.error("HttpMessageConversionException caught: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>("MISSING_BODY", false, "Missing request body.", null));
    }

    @ExceptionHandler(InvitationException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvitationException(InvitationException ex) {
        log.error("InvitationException caught: {} ", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("INTERNAL_ERROR",false, "An error occurred while sending the invitation.", null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotValidException(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>("VALIDATION_FAILED", false, "Some fields are invalid. Please correct the errors and try again.", null));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception e) {
        log.error("Unhandled exception caught: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("INTERNAL_ERROR", false, "An internal error has occurred.", null));
    }
}

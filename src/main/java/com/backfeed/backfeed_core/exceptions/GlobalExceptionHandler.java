package com.backfeed.backfeed_core.exceptions;

import com.backfeed.backfeed_core.exceptions.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An internal error has occurred.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "User not found."
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<ErrorResponse> handleJwtValidationException(JwtValidationException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_ACCEPTABLE,
                "Token is invalid."
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleJwtTokenExpiredException(JwtTokenExpiredException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                "Authentication token has expired."
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                "Invalid username or password."
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "Role not found."
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MailException.class, MailSendException.class})
    public ResponseEntity<ErrorResponse> handleMailNotSentException(Exception ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "A mail delivery error occurred."
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvitationAlreadyPending.class)
    public ResponseEntity<ErrorResponse> handleExistingInvitation(InvitationAlreadyPending ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invitation already sent."
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInvitationNotFound(InvitationNotFoundException ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "Invitation not found."
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInvitation.class)
    public ResponseEntity<ErrorResponse> handleInvalidInvitation(InvalidInvitation ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invitation not valid."
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExists ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "User already exists."
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDenied.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDenied ex){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                "Access denied."
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }




}

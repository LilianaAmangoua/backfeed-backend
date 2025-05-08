package com.backfeed.backfeed_core.controllers;

import com.backfeed.backfeed_core.dtos.RegisterRequest;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.exceptions.responses.SuccessResponse;
import com.backfeed.backfeed_core.security.JwtToken;
import com.backfeed.backfeed_core.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Handles user registration and login using JWT.")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account using the provided registration details and a valid invitation token.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully created."),
            }
    )
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.CREATED, "User successfully created."));
    }

    @Operation(
            summary = "Authenticate a user",
            description = "Logs in a user using email and password, and returns a JWT token in response.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully logged in."),
            }
    )
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<JwtToken>> login(@RequestBody User user) {
        JwtToken token = authService.login(user);
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, "User successfully logged in.", token));
    }
}


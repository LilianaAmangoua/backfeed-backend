package com.backfeed.backfeed_core.controllers;

import com.backfeed.backfeed_core.dtos.RegisterRequest;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.exceptions.responses.ApiResult;
import com.backfeed.backfeed_core.security.JwtToken;
import com.backfeed.backfeed_core.services.AuthService;
import com.backfeed.backfeed_core.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Handles user registration and login using JWT.")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account using the provided registration details and a valid invitation token."
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('PO')")
    @PostMapping("/register")
    public ResponseEntity<ApiResult<Void>> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.success("User successfully created.", HttpStatus.CREATED));
    }

    @Operation(
            summary = "Authenticate a user",
            description = "Logs in a user using email and password, and returns a JWT token in response."
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResult<JwtToken>> login(@RequestBody User user) {
        JwtToken token = authService.login(user);
        return ResponseEntity.ok(ApiResult.success("User successfully logged in.", HttpStatus.OK, token));
    }
}

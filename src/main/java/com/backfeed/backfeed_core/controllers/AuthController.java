package com.backfeed.backfeed_core.controllers;

import com.backfeed.backfeed_core.dtos.RegisterRequest;
import com.backfeed.backfeed_core.entities.CustomUserDetails;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.exceptions.responses.SuccessResponse;
import com.backfeed.backfeed_core.repositories.RoleRepository;
import com.backfeed.backfeed_core.repositories.UserRepository;
import com.backfeed.backfeed_core.security.JwtToken;
import com.backfeed.backfeed_core.security.JwtUtil;
import com.backfeed.backfeed_core.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Void>> register(@Valid @RequestBody RegisterRequest request){
        authService.register(request);
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.CREATED, "User successfully created."));
    }


    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<JwtToken>> login(@RequestBody User user){
        JwtToken token = authService.login(user);
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, "User successfully logged in.", token));
    }
}

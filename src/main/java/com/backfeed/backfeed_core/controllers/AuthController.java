package com.backfeed.backfeed_core.controllers;

import com.backfeed.backfeed_core.entities.Role;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.exceptions.responses.ErrorResponse;
import com.backfeed.backfeed_core.exceptions.responses.SuccessResponse;
import com.backfeed.backfeed_core.repositories.RoleRepository;
import com.backfeed.backfeed_core.repositories.UserRepository;
import com.backfeed.backfeed_core.security.JwtToken;
import com.backfeed.backfeed_core.security.JwtUtil;
import jakarta.validation.Valid;
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

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user){
        boolean doesExist = userRepository.existsByEmail(user.getEmail());
        if (doesExist) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request", "This user already exists."), HttpStatus.BAD_REQUEST);
        }

        User newUser = new User(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getRole()
        );

        userRepository.save(newUser);

        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.CREATED, "User successfully created."));
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        JwtToken token = jwtUtil.generateToken(user.getEmail(), user.getFirstName());
        return ResponseEntity.ok(new SuccessResponse<JwtToken>(HttpStatus.OK, "User successfully logged in.", token));
    }
}

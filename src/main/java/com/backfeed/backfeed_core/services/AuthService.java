package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.dtos.LoginRequest;
import com.backfeed.backfeed_core.entities.*;
import com.backfeed.backfeed_core.exceptions.*;
import com.backfeed.backfeed_core.security.JwtToken;
import com.backfeed.backfeed_core.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }


    public JwtToken login(LoginRequest request){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Integer userId = ((CustomUserDetails) userDetails).getUser().getId();
            return jwtUtil.generateToken(userId);
        } catch(BadCredentialsException e){
            throw new UserNotFoundException("Invalid username or password : " + e);
        }
    }

}

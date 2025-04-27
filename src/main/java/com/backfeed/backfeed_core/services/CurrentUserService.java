package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.entities.CustomUserDetails;
import com.backfeed.backfeed_core.exceptions.JwtValidationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    public Integer getCurrentUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new JwtValidationException("Unauthenticated user. Please login or register.");
        }
        return ((CustomUserDetails) auth.getPrincipal()).getUserId();
    }
}

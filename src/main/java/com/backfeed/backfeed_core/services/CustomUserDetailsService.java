package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.entities.CustomUserDetails;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.exceptions.UserNotFoundException;
import com.backfeed.backfeed_core.repositories.UserRepository;
import com.backfeed.backfeed_core.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email : " + email + " doesn't exist."));
        log.warn("User doesn't exist : {}", email);

        return new CustomUserDetails(user);
    }
}

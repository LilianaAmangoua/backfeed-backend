package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.entities.CustomUserDetails;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.exceptions.UserNotFound;
import com.backfeed.backfeed_core.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFound("L'utilisateur avec l'adresse mail : " + email + " n'existe pas."));
        return new CustomUserDetails(user);
    }
}

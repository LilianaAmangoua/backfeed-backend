package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.entities.CustomUserDetails;
import com.backfeed.backfeed_core.entities.Role;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.repositories.*;
import com.backfeed.backfeed_core.security.JwtToken;
import com.backfeed.backfeed_core.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private InvitationRepository invitationRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PlaceholderClientRepository placeholderClientRepository;
    @Mock
    private HierarchyRepository hierarchyRepository;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private AuthenticationManager authenticationManager;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    @InjectMocks
    private AuthService service;

    @Test
    void shouldLogTheUser(){
        Role role = new Role("PO");
        User user = User.builder()
            .firstName("Po")
            .lastName("lastname")
            .email("po@company.com")
            .password("password")
            .role(role)
            .build();

        user.setId(2);

        CustomUserDetails userDetails = new CustomUserDetails(user);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.generateToken(2)).thenReturn(new JwtToken("fake token"));

        service.login(user);

        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        verify(jwtUtil).generateToken(2);


    }
}

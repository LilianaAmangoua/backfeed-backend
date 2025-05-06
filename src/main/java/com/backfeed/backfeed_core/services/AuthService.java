package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.dtos.RegisterRequest;
import com.backfeed.backfeed_core.entities.*;
import com.backfeed.backfeed_core.enums.InvitationStatus;
import com.backfeed.backfeed_core.exceptions.*;
import com.backfeed.backfeed_core.repositories.InvitationRepository;
import com.backfeed.backfeed_core.repositories.PlaceholderClientRepository;
import com.backfeed.backfeed_core.repositories.RoleRepository;
import com.backfeed.backfeed_core.repositories.UserRepository;
import com.backfeed.backfeed_core.security.JwtToken;
import com.backfeed.backfeed_core.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.backfeed.backfeed_core.enums.InvitationStatus.ACCEPTED;
import static com.backfeed.backfeed_core.enums.InvitationStatus.PENDING;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final InvitationRepository invitationRepository;
    private final RoleRepository roleRepository;
    private final PlaceholderClientRepository placeholderClientRepository;
    private final InvitationService invitationService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, InvitationRepository invitationRepository, RoleRepository roleRepository, PlaceholderClientRepository placeholderClientRepository, InvitationService invitationService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.invitationRepository = invitationRepository;
        this.roleRepository = roleRepository;
        this.placeholderClientRepository = placeholderClientRepository;
        this.invitationService = invitationService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterRequest registerRequest){
        User registeringUser = registerRequest.getUser();
        String token = registerRequest.getToken();
        doesUserExist(registeringUser.getEmail());
        Invitation invitation = isRequestValid(token);

        Role assignedRole = roleRepository.findByName(invitation.getRoleAssigned());

        Company company = assignCompany(registeringUser.getEmail());
        User newUser = buildUser(registeringUser, assignedRole, company);

        userRepository.save(newUser);
        invitation.setInvitationStatus(ACCEPTED);

        userRepository.save(newUser);
    }

    public void login(User user, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Integer userId = ((CustomUserDetails) userDetails).getUser().getId();
        JwtToken token = jwtUtil.generateToken(userId);

        ResponseCookie cookie = ResponseCookie.from("jwt", token.getToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofSeconds(jwtExpirationMs))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private Invitation isRequestValid(String token){
        boolean isTokenValid = jwtUtil.validateJwtToken(token);
        String invitationId = jwtUtil.getDataFromToken(token);

        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationNotFoundException("Invitation not found"));

        InvitationStatus status = invitation.getInvitationStatus();
        LocalDateTime expiryDate = invitation.getExpiryDate();
        LocalDateTime now = LocalDateTime.now();

        if(status != PENDING){
            throw new InvalidInvitation("Invitation is invalid.");
        }

        if(expiryDate.isBefore(now)){
            invitation.setInvitationStatus(InvitationStatus.EXPIRED);
            invitationRepository.save(invitation);
            throw new InvalidInvitation("Invitation is expired.");
        }
        return invitation;
    }

    private void doesUserExist(String email){
        boolean doesExist = userRepository.existsByEmail(email);
        if (doesExist) {
            log.info("User with email {} already exists", email);
            throw new UserAlreadyExists("User already exists");
        }
    }

    private Company assignCompany(String email){
        Optional<PlaceholderClient> placeholderClient = placeholderClientRepository.findByEmail(email);
        return placeholderClient.map(PlaceholderClient::getCompany).orElse(null);
    }

    private User buildUser(User rawUser, Role role, Company company) {
        User user = new User();
        user.setFirstName(rawUser.getFirstName());
        user.setLastName(rawUser.getLastName());
        user.setEmail(rawUser.getEmail());
        user.setPassword(passwordEncoder.encode(rawUser.getPassword()));
        user.setRole(role);
        user.setCompany(company);
        return user;
    }

}

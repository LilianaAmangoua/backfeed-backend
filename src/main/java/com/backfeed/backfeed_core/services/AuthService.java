package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.controllers.AuthController;
import com.backfeed.backfeed_core.dtos.RegisterRequest;
import com.backfeed.backfeed_core.entities.Invitation;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.enums.InvitationStatus;
import com.backfeed.backfeed_core.exceptions.InvalidInvitation;
import com.backfeed.backfeed_core.exceptions.InvitationAlreadyPending;
import com.backfeed.backfeed_core.exceptions.InvitationNotFoundException;
import com.backfeed.backfeed_core.exceptions.UserAlreadyExists;
import com.backfeed.backfeed_core.repositories.InvitationRepository;
import com.backfeed.backfeed_core.repositories.UserRepository;
import com.backfeed.backfeed_core.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.backfeed.backfeed_core.enums.InvitationStatus.PENDING;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    private final JwtUtil jwtUtil;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository userRepository, InvitationRepository invitationRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest registerRequest){
        User user = registerRequest.getUser();
        String token = registerRequest.getToken();
        boolean doesExist = userRepository.existsByEmail(user.getEmail());
        if (doesExist) {
            log.warn("User with id {} already exists", user.getId());
            throw new UserAlreadyExists("User already exists");
        }

        isRequestValid(token);

    }

    private void isRequestValid(String token){
        //TODO: Vérifier le token aussi
        Invitation invitation = invitationRepository.findByToken(token).orElseThrow(() -> new InvitationNotFoundException("Invitation not found"));
        InvitationStatus status = invitation.getInvitationStatus();
        LocalDateTime expiryDate = invitation.getExpiryDate();
        LocalDateTime now = LocalDateTime.now();
        if(status != PENDING){
            throw new InvalidInvitation("Invitation is invalid.");
        }

        if(expiryDate.isBefore(now)){
            throw new InvalidInvitation("Invitation is expired.");
        }

    }
}

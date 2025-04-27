package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.dtos.InvitationRequest;
import com.backfeed.backfeed_core.entities.Invitation;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.exceptions.UserNotFoundException;
import com.backfeed.backfeed_core.repositories.InvitationRepository;
import com.backfeed.backfeed_core.repositories.UserRepository;
import com.backfeed.backfeed_core.security.JwtToken;
import com.backfeed.backfeed_core.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.backfeed.backfeed_core.enums.InvitationStatus.PENDING;

@Service
public class InvitationService {
    private JwtUtil jwtUtil;
    private InvitationRepository invitationRepository;
    private UserRepository userRepository;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    private CurrentUserService currentUserService;

    public InvitationService() {
    }

    public InvitationService(JwtUtil jwtUtil, InvitationRepository invitationRepository, UserRepository userRepository, int jwtExpirationMs, CurrentUserService currentUserService) {
        this.jwtUtil = jwtUtil;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.jwtExpirationMs = jwtExpirationMs;
        this.currentUserService = currentUserService;
    }

    public void sendInvitation(InvitationRequest request){
        Integer id = currentUserService.getCurrentUserId();
        JwtToken token = jwtUtil.generateToken(request.getEmail(), request.getFirstName());
        String link = generateLink(token);
        User productOwner = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found."));
        Invitation invitation = new Invitation(request.getEmail(), productOwner, "CLIENT", LocalDateTime.now().plusSeconds(jwtExpirationMs), PENDING );
        invitationRepository.save(invitation);
    }

    private String generateLink(JwtToken token){
        return "http://locahost:8080/register?=token=" + token;
    }

    private void sendEmail(){

    }
}

package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.dtos.InvitationRequest;
import com.backfeed.backfeed_core.dtos.InvitationToken;
import com.backfeed.backfeed_core.entities.Invitation;
import com.backfeed.backfeed_core.entities.Role;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.enums.InvitationStatus;
import com.backfeed.backfeed_core.exceptions.AccessDenied;
import com.backfeed.backfeed_core.exceptions.GlobalExceptionHandler;
import com.backfeed.backfeed_core.exceptions.InvitationAlreadyPending;
import com.backfeed.backfeed_core.exceptions.UserNotFoundException;
import com.backfeed.backfeed_core.repositories.InvitationRepository;
import com.backfeed.backfeed_core.repositories.UserRepository;
import com.backfeed.backfeed_core.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InvitationService {

    private JwtUtil jwtUtil;
    private InvitationRepository invitationRepository;
    private UserRepository userRepository;
    @Value("${invitation.expiration}")
    private int invitationExpiration;
    private CurrentUserService currentUserService;
    private JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger(InvitationService.class);

    public InvitationService() {
    }

    @Autowired
    public InvitationService(JwtUtil jwtUtil, InvitationRepository invitationRepository, UserRepository userRepository, CurrentUserService currentUserService, JavaMailSender mailSender) {
        this.jwtUtil = jwtUtil;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
        this.mailSender = mailSender;
    }


    public Invitation sendInvitation(InvitationRequest request) {
        Integer id = currentUserService.getCurrentUserId();
        User inviter = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        String role = assignInvitedRole(inviter);
        validateNoPendingInvitation(request.getEmail());

        Invitation invitation = createInvitation(request.getEmail(), inviter, role);
        String link = generateLink(invitation.getToken());

        try {
            sendEmail(request, link);
        } catch (MailException e) {
            log.error("Failed to send invitation email to {} : {}", request.getEmail(), e.getMessage());
            return null;
        }

        return invitationRepository.save(invitation);
    }

    private void validateNoPendingInvitation(String email) {
        if (invitationRepository.isAlreadyInvited(email, InvitationStatus.PENDING).isPresent()) {
            log.warn("User tried to send a second invitation to: {}", email);
            throw new InvitationAlreadyPending("Invitation already sent.");
        }
    }

    private Invitation createInvitation(String email, User inviter, String role) {
        Invitation invitation = new Invitation(
                email,
                inviter,
                role,
                LocalDateTime.now().plusSeconds(invitationExpiration),
                InvitationStatus.PENDING
        );

        String token = generateTokenAndAssignId(invitation);
        invitation.setToken(token);

        return invitation;
    }

    private String generateTokenAndAssignId(Invitation invitation) {
        String id = UUID.randomUUID().toString();
        invitation.setId(id);
        InvitationToken invitationToken = jwtUtil.generateInvitationToken(id);
        return invitationToken.getToken();
    }


    private String generateLink(String token){
        return "http://locahost:8080/register?=token=" + token;
    }

    private void sendEmail(InvitationRequest invitationRequest, String link){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(invitationRequest.getEmail());
        mailMessage.setSubject("Votre lien d'invitation BackFeed");
        mailMessage.setText(invitationRequest.getFirstName() + ", voici votre lien d'invitation : " + link + ". /n " +
                "L'équipe Backfeed");

        mailSender.send(mailMessage);
    }

    private String assignInvitedRole(User inviter){
        Role role = inviter.getRole();
        if(role.getName().equals("SUPER_ADMIN")){
            return "PO";
        } else if(role.getName().equals("PO")){
            return "CLIENT";
        } else {
            log.warn("User {} tried to send an invitation with this role : {}", inviter.getId(), role.getName());
            throw new AccessDenied("Cannot send invitation.");
        }
    }

}





package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.dtos.InvitationRequest;
import com.backfeed.backfeed_core.entities.Invitation;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.enums.InvitationStatus;
import com.backfeed.backfeed_core.exceptions.InvitationAlreadyPending;
import com.backfeed.backfeed_core.exceptions.UserNotFoundException;
import com.backfeed.backfeed_core.repositories.InvitationRepository;
import com.backfeed.backfeed_core.repositories.UserRepository;
import com.backfeed.backfeed_core.security.JwtToken;
import com.backfeed.backfeed_core.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.backfeed.backfeed_core.enums.InvitationStatus.PENDING;

@Service
public class InvitationService {
    private JwtUtil jwtUtil;
    private InvitationRepository invitationRepository;
    private UserRepository userRepository;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    private CurrentUserService currentUserService;
    private JavaMailSender mailSender;

    public InvitationService() {
    }

    public InvitationService(JwtUtil jwtUtil, InvitationRepository invitationRepository, UserRepository userRepository, int jwtExpirationMs, CurrentUserService currentUserService, JavaMailSender mailSender) {
        this.jwtUtil = jwtUtil;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.jwtExpirationMs = jwtExpirationMs;
        this.currentUserService = currentUserService;
        this.mailSender = mailSender;
    }


    public Invitation sendInvitation(InvitationRequest request){
        Integer id = currentUserService.getCurrentUserId();
        JwtToken token = jwtUtil.generateToken(request.getEmail(), request.getFirstName());
        String link = generateLink(token);

        User productOwner = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found."));
        sendEmail(request, link);

        Invitation invitation = new Invitation(request.getEmail(), productOwner, "CLIENT", LocalDateTime.now().plusSeconds(jwtExpirationMs), PENDING);
        Optional<Invitation> existingInvitation = invitationRepository.isAlreadyInvited(invitation.getInvitedEmail(), InvitationStatus.PENDING);
        if(existingInvitation.isEmpty()){
           return invitationRepository.save(invitation);
        }

        throw new InvitationAlreadyPending("You cannot send another invitation to this email : " + request.getEmail());
    }

    private String generateLink(JwtToken token){
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

    // Changer l'état de l'invitation à l'expiration ou à l'acceptation
}

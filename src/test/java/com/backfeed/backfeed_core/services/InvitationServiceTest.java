package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.dtos.InvitationRequest;
import com.backfeed.backfeed_core.dtos.InvitationToken;
import com.backfeed.backfeed_core.entities.Invitation;
import com.backfeed.backfeed_core.entities.Role;
import com.backfeed.backfeed_core.entities.User;
import com.backfeed.backfeed_core.repositories.InvitationRepository;
import com.backfeed.backfeed_core.repositories.UserRepository;
import com.backfeed.backfeed_core.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvitationServiceTest {

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CurrentUserService currentUserService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private JavaMailSender mailSender;


    @InjectMocks
    private InvitationService invitationService;

    @Test
    void shouldSendAnInvitation(){

        InvitationRequest request = new InvitationRequest("hi@gmail.com", "Hi");
        Role role = new Role("PO");
        User inviter = new User("Po", "lastname", "po@company.com", "password", role);
        InvitationToken token = new InvitationToken("fakeToken", new Date());
        when(currentUserService.getCurrentUserId()).thenReturn(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(inviter));
        when(jwtUtil.generateInvitationToken(anyString())).thenReturn(token);

        invitationService.sendInvitation(request);

        verify(currentUserService).getCurrentUserId();
        verify(userRepository).findById(1);
        verify(invitationRepository).save(any(Invitation.class));
        verify(mailSender).send(any(SimpleMailMessage.class));

    }
}

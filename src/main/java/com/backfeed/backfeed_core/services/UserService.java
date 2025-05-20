package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.dtos.RegisterRequest;
import com.backfeed.backfeed_core.entities.*;
import com.backfeed.backfeed_core.enums.InvitationStatus;
import com.backfeed.backfeed_core.exceptions.InvalidInvitation;
import com.backfeed.backfeed_core.exceptions.InvitationNotFoundException;
import com.backfeed.backfeed_core.exceptions.UserAlreadyExists;
import com.backfeed.backfeed_core.exceptions.UserNotFoundException;
import com.backfeed.backfeed_core.repositories.*;
import com.backfeed.backfeed_core.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.backfeed.backfeed_core.enums.InvitationStatus.ACCEPTED;
import static com.backfeed.backfeed_core.enums.InvitationStatus.PENDING;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final InvitationRepository invitationRepository;
    private final RoleRepository roleRepository;
    private final PlaceholderClientRepository placeholderClientRepository;
    private final HierarchyRepository hierarchyRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, InvitationRepository invitationRepository, RoleRepository roleRepository, PlaceholderClientRepository placeholderClientRepository, HierarchyRepository hierarchyRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.invitationRepository = invitationRepository;
        this.roleRepository = roleRepository;
        this.placeholderClientRepository = placeholderClientRepository;
        this.hierarchyRepository = hierarchyRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public void register(RegisterRequest registerRequest){
        String email = registerRequest.getEmail();
        String token = registerRequest.getToken();
        doesUserExist(email);
        Invitation invitation = isRequestValid(token);
        Integer inviterId = invitation.getUser().getId();
        User inviter = userRepository.findById(inviterId).orElseThrow(() -> new UserNotFoundException("Inviter with id : " + inviterId + " not found."));

        Role assignedRole = roleRepository.findByName(invitation.getRoleAssigned());

        Company company = assignCompany(email);
        User newUser = buildUser(registerRequest, assignedRole, company);

        userRepository.save(newUser);
        invitation.setInvitationStatus(ACCEPTED);
        invitationRepository.save(invitation);

        Hierarchy hierarchy = new Hierarchy(inviter, newUser, LocalDateTime.now());
        hierarchyRepository.save(hierarchy);

    }

    @Transactional
    private Invitation isRequestValid(String token){
        boolean isTokenValid = jwtUtil.validateJwtToken(token);
        String invitationId = jwtUtil.getDataFromToken(token);

        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationNotFoundException("Invitation not found. Register request is invalid."));

        InvitationStatus status = invitation.getInvitationStatus();
        LocalDateTime expiryDate = invitation.getExpiryDate();
        LocalDateTime now = LocalDateTime.now();

        if(status != PENDING){
            throw new InvalidInvitation("Invitation is invalid. Status is : " + status);
        }

        if(expiryDate.isBefore(now)){
            invitation.setInvitationStatus(InvitationStatus.EXPIRED);
            invitationRepository.save(invitation);
            throw new InvalidInvitation("Invitation is expired : " + expiryDate);
        }
        return invitation;
    }

    private void doesUserExist(String email){
        boolean doesExist = userRepository.existsByEmail(email);
        if (doesExist) {
            throw new UserAlreadyExists("User with email " + email + "already exists");
        }
    }

    private Company assignCompany(String email){
        Optional<PlaceholderClient> placeholderClient = placeholderClientRepository.findByEmail(email);
        return placeholderClient.map(PlaceholderClient::getCompany).orElse(null);
    }

    private User buildUser(RegisterRequest request, Role role, Company company) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setCompany(company);
        return user;
    }
}

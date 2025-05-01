package com.backfeed.backfeed_core.entities;

import com.backfeed.backfeed_core.enums.InvitationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invitation")
public class Invitation {
    @Id
    private String id;
    private String invitedEmail;

    @ManyToOne
    @JoinColumn(name = "inviter_id")
    private User user;

    private String roleAssigned;
    private LocalDateTime expiryDate;
    private InvitationStatus invitationStatus;
    private String token;


    public Invitation() {
    }

    public Invitation(String id, String invitedEmail, User user, String roleAssigned, LocalDateTime expiryDate, InvitationStatus invitationStatus) {
        this.id = id;
        this.invitedEmail = invitedEmail;
        this.user = user;
        this.roleAssigned = roleAssigned;
        this.expiryDate = expiryDate;
        this.invitationStatus = invitationStatus;
    }

    public Invitation(String invitedEmail, User user, String roleAssigned, LocalDateTime expiryDate, InvitationStatus invitationStatus) {
        this.invitedEmail = invitedEmail;
        this.user = user;
        this.roleAssigned = roleAssigned;
        this.expiryDate = expiryDate;
        this.invitationStatus = invitationStatus;
    }

    public Invitation(String invitedEmail, User user, String roleAssigned, LocalDateTime expiryDate, InvitationStatus invitationStatus, String token) {
        this.invitedEmail = invitedEmail;
        this.user = user;
        this.roleAssigned = roleAssigned;
        this.expiryDate = expiryDate;
        this.invitationStatus = invitationStatus;
        this.token = token;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvitedEmail() {
        return invitedEmail;
    }

    public void setInvitedEmail(String invitedEmail) {
        this.invitedEmail = invitedEmail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRoleAssigned() {
        return roleAssigned;
    }

    public void setRoleAssigned(String roleAssigned) {
        this.roleAssigned = roleAssigned;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public InvitationStatus getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(InvitationStatus invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

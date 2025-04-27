package com.backfeed.backfeed_core.entities;

import com.backfeed.backfeed_core.enums.InvitationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invitation")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String invitedEmail;

    @ManyToOne
    @JoinColumn(name = "inviter_id")
    private User user;

    private String roleAssigned;
    private LocalDateTime expiryDate;
    private InvitationStatus invitationStatus;


    public Invitation() {
    }

    public Invitation(Integer id, String invitedEmail, User user, String roleAssigned, LocalDateTime expiryDate, InvitationStatus invitationStatus) {
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}

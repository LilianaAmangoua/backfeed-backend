package com.backfeed.backfeed_core.entities;

import com.backfeed.backfeed_core.enums.InvitationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invitation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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


}

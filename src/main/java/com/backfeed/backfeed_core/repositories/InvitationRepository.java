package com.backfeed.backfeed_core.repositories;

import com.backfeed.backfeed_core.entities.Invitation;
import com.backfeed.backfeed_core.enums.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
    @Query("SELECT i FROM Invitation i WHERE i.invitedEmail = :email AND i.invitationStatus = :status")
    Optional<Invitation> isAlreadyInvited(@Param("email") String email, @Param("status") InvitationStatus status);
}


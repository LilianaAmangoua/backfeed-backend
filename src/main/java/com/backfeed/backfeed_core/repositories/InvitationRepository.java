package com.backfeed.backfeed_core.repositories;

import com.backfeed.backfeed_core.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Integer> {

}

package com.backfeed.backfeed_core.repositories;

import com.backfeed.backfeed_core.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}

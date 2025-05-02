package com.backfeed.backfeed_core.repositories;

import com.backfeed.backfeed_core.entities.PlaceholderClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceholderClientRepository extends JpaRepository<PlaceholderClient, Integer> {
    Optional<PlaceholderClient> findByEmail(String email);
}

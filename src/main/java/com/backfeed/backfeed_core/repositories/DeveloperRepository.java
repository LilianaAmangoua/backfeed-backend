package com.backfeed.backfeed_core.repositories;

import com.backfeed.backfeed_core.entities.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
}

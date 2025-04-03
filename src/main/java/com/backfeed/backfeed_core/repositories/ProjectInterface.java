package com.backfeed.backfeed_core.repositories;

import com.backfeed.backfeed_core.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectInterface extends JpaRepository<Project, Integer> {
}

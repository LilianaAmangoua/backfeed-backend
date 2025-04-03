package com.backfeed.backfeed_core.repository;

import com.backfeed.backfeed_core.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

}

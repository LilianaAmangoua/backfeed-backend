package com.backfeed.backfeed_core.repositories;

import com.backfeed.backfeed_core.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByUserId(Integer userId);
}

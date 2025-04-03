package com.backfeed.backfeed_core.services;

import com.backfeed.backfeed_core.entities.Feedback;
import com.backfeed.backfeed_core.repositories.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> getAllUserFeedbacks(Integer userId){
        try {
            return feedbackRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

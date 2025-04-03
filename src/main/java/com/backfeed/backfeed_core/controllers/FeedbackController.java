package com.backfeed.backfeed_core.controllers;

import com.backfeed.backfeed_core.entities.Feedback;
import com.backfeed.backfeed_core.services.FeedbackService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping ("/all")
    public List<Feedback> getAllUserFeedbacks(@PathVariable Integer userId){
        return feedbackService.getAllUserFeedbacks(userId);
    }

}

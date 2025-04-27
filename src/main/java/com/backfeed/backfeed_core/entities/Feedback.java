package com.backfeed.backfeed_core.entities;

import com.backfeed.backfeed_core.enums.FeedbackStatus;
import com.backfeed.backfeed_core.enums.FeedbackType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private FeedbackType feedbackType;

    private FeedbackStatus status;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "feedback")
    private FeedbackAnswer feedbackAnswer;


    public Feedback() {
    }


    public Feedback(Integer id, User user, String title, FeedbackType feedbackType, FeedbackStatus status, String content, LocalDateTime createdAt, LocalDateTime updatedAt, FeedbackAnswer feedbackAnswer) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.feedbackType = feedbackType;
        this.status = status;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.feedbackAnswer = feedbackAnswer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public FeedbackAnswer getFeedbackAnswer() {
        return feedbackAnswer;
    }

    public void setFeedbackAnswer(FeedbackAnswer feedbackAnswer) {
        this.feedbackAnswer = feedbackAnswer;
    }
}


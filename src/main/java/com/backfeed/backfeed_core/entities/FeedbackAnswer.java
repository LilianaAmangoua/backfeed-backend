package com.backfeed.backfeed_core.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback_answer")
public class FeedbackAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @ManyToOne
    @JoinColumn(name = "po_id")
    private User po;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FeedbackAnswer() {
    }

    public FeedbackAnswer(Integer id, Feedback feedback, User po, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.feedback = feedback;
        this.po = po;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public User getPo() {
        return po;
    }

    public void setPo(User po) {
        this.po = po;
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
}

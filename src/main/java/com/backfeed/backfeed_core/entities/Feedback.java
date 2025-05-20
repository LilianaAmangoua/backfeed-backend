package com.backfeed.backfeed_core.entities;

import com.backfeed.backfeed_core.enums.FeedbackStatus;
import com.backfeed.backfeed_core.enums.FeedbackType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "feedback")
@NoArgsConstructor
@AllArgsConstructor
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


}


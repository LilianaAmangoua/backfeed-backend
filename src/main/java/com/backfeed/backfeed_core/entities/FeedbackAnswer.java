package com.backfeed.backfeed_core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback_answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}

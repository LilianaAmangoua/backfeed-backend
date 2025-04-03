package com.backfeed.backfeed_core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feedback")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Column(name = "feedback_type")
    private String feedbackType;

    private String status;
    private String content;

    @OneToOne(mappedBy ="feedback")
    private Ticket ticket;
}



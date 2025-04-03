package com.backfeed.backfeed_core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ticket")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Chaque ticket appartient à un seul developer
    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @OneToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    // Chaque ticket appartient à un seul PO
    @ManyToOne
    @JoinColumn(name = "product_owner_id")
    private User productOwner;

    private String title;
    @Column(name = "feedback_type")
    private String feedbackType;
    private String status;
    private String priority;
    private String content;

}

package com.backfeed.backfeed_core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "developer")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name= "product_owner_id")
    private User productOwner;

    @OneToMany(mappedBy = "developer")
    private Set<Ticket> tickets;
}


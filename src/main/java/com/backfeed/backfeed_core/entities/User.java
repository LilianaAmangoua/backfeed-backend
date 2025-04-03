package com.backfeed.backfeed_core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String role;
    private String email;
    private String name;
    private String password;

    @Column(name = "team_code")
    private String teamCode;

    @OneToMany(mappedBy = "user")
    private Set<Project> projects;

    @OneToMany(mappedBy = "user")
    private Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "productOwner")
    private Set<Developer> developers;
}

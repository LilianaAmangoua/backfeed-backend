package com.backfeed.backfeed_core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "placeholder_client")
@Getter
@Setter
public class PlaceholderClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name= "first_name")
    @NotNull(message = "Firstname cannot be null.")
    private String firstName;
    @Column(name = "last_name")
    @NotNull(message = "Lastname cannot be null.")
    private String lastName;
    @Email(message = "Please insert a valid email address.")
    private String email;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "client")
    private Set<Project> projects;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


    public PlaceholderClient() {
    }

    public PlaceholderClient(Integer id, String firstName, String lastName, String email, LocalDateTime createdAt, User createdBy) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}

package com.backfeed.backfeed_core.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "placeholder_id")
    private PlaceholderClient client;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User user;

    private String name;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Project() {
    }

    public Project(Integer id, User user, String name, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.createdAt = createdAt;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

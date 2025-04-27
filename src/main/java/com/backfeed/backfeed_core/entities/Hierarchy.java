package com.backfeed.backfeed_core.entities;

import com.backfeed.backfeed_core.entities.composite_keys.HierarchyId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@IdClass(HierarchyId.class)
@Table(name = "hierarchy")
public class Hierarchy {
    @Id
    private Integer supervisorId;

    @Id
    private Integer subordinateId;

    @ManyToOne
    @JoinColumn(name = "supervisor_id", insertable = false, updatable = false)
    private User supervisor;

    @ManyToOne
    @JoinColumn(name = "subordinate_id", insertable = false, updatable = false)
    private User subordinate;


    private LocalDateTime creationDate;

    public Hierarchy() {
    }

    public Hierarchy(User supervisor, User subordinate, LocalDateTime creationDate) {
        this.supervisor = supervisor;
        this.subordinate = subordinate;
        this.supervisorId = supervisor.getId();
        this.subordinateId = subordinate.getId();
        this.creationDate = creationDate;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
        this.supervisorId = supervisor.getId();
    }

    public User getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(User subordinate) {
        this.subordinate = subordinate;
        this.subordinateId = subordinate.getId();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Integer supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Integer getSubordinateId() {
        return subordinateId;
    }

    public void setSubordinateId(Integer subordinateId) {
        this.subordinateId = subordinateId;
    }
}

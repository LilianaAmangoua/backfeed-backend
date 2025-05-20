package com.backfeed.backfeed_core.entities;

import com.backfeed.backfeed_core.entities.composite_keys.HierarchyId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@IdClass(HierarchyId.class)
@Table(name = "hierarchy")
@Getter
@Setter
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
        if (supervisor == null || subordinate == null) {
            throw new IllegalArgumentException("Supervisor and subordinate cannot be null");
        }

        this.supervisor = supervisor;
        this.subordinate = subordinate;

        this.supervisorId = supervisor.getId();
        this.subordinateId = subordinate.getId();

        this.creationDate = creationDate;
    }

}

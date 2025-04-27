package com.backfeed.backfeed_core.entities.composite_keys;

import java.io.Serializable;
import java.util.Objects;

public class HierarchyId implements Serializable {
    private Integer supervisorId;
    private Integer subordinateId;

    public HierarchyId() {
    }

    public HierarchyId(Integer supervisorId, Integer subordinateId) {
        this.supervisorId = supervisorId;
        this.subordinateId = subordinateId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HierarchyId that = (HierarchyId) o;
        return Objects.equals(supervisorId, that.supervisorId) && Objects.equals(subordinateId, that.subordinateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supervisorId, subordinateId);
    }
}

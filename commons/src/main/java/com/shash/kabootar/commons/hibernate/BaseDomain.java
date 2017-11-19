package com.shash.kabootar.commons.hibernate;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author shashankgautam
 */
@MappedSuperclass
public class BaseDomain {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Getter
    @Setter
    private Timestamp lastUpdatedAt = new Timestamp(System.currentTimeMillis());

    /**
     * Sets created and updated timestamp before persisting.
     */
    @PrePersist
    public void prePersist() {
        final Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        this.setCreatedAt(currentTime);
        this.setLastUpdatedAt(currentTime);
    }

    /**
     * Sets created and updated timestamp before saveOrUpdate.
     */
    @PreUpdate
    public void preUpdate() {
        this.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }
}

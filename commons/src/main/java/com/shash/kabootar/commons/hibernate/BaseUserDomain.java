package com.shash.kabootar.commons.hibernate;

import com.shash.kabootar.commons.filter.UserContext;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * @author shashankgautam
 */
@MappedSuperclass
public class BaseUserDomain extends BaseDomain {

    @Getter
    @Setter
    private String createdBy;

    @Getter
    @Setter
    private String lastUpdatedBy;

    /**
     * Sets createdBy
     */
    @PrePersist
    public void prePersist() {
        super.prePersist();
        this.setCreatedBy(UserContext.instance().getUser());
    }

    /**
     * Sets updatedBy
     */
    @PreUpdate
    public void preUpdate() {
        super.preUpdate();
        this.setLastUpdatedBy(UserContext.instance().getUser());
    }
}

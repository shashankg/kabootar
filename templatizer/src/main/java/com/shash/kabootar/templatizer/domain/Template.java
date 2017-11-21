package com.shash.kabootar.templatizer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shash.kabootar.commons.filter.UserContext;
import com.shash.kabootar.commons.hibernate.BaseUserDomain;
import com.shash.kabootar.templatizer.TemplateConverter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shashankgautam
 */
@Entity
@Table(name = "templates")
@Access(AccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    public enum Status {
        CREATED, PUBLISHED, UNPUBLISHED
    }

    @Id
    @Getter
    private String id;

    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    private String templateString;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    @Getter
    @Setter
    private boolean deleted = false;

    @Getter
    @Setter
    @NotNull
    private String tenant;

    @Getter
    @Setter
    private int version = 1;

    @Getter
    @Setter
    private String createdBy;

    @Getter
    @Setter
    private String lastUpdatedBy;

    @Getter
    @Setter
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Getter
    @Setter
    private Timestamp lastUpdatedAt = new Timestamp(System.currentTimeMillis());

    /**
     * Sets createdBy
     */
    @PrePersist
    public void prePersist() {
        this.id = createId(getTenant(), getName());
        final Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        this.setCreatedAt(currentTime);
        this.setLastUpdatedAt(currentTime);
        this.setCreatedBy(UserContext.instance().getUser());
    }

    /**
     * Sets updatedBy
     */
    @PreUpdate
    public void preUpdate() {
        this.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        this.setLastUpdatedBy(UserContext.instance().getUser());
    }


    /**
     * @param tenant ::
     * @param name   ::
     * @return id
     */
    public static String createId(String tenant, String name) {
        return String.format("%s:%s", tenant, name);
    }
}

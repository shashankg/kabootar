package com.shash.kabootar.templatizer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shash.kabootar.commons.hibernate.BaseUserDomain;
import com.shash.kabootar.templatizer.TemplateConverter;
import lombok.*;

import javax.persistence.*;

/**
 * @author shashankgautam
 */
@Getter
@Setter
@Entity
@Table(name = "templates")
@Access(AccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Template extends BaseUserDomain {

    public enum Status {
        CREATED, PUBLISHED, UNPUBLISHED
    }

    @Convert(converter = TemplateConverter.class)
    private String templateString;

    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    private Boolean deleted = false;

    private String tenant;

    private int version = 1;
}

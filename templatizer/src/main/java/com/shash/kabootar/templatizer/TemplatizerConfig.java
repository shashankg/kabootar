package com.shash.kabootar.templatizer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author shashankgautam
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplatizerConfig extends Configuration {


    @NotNull
    @Valid
    @Getter
    private DataSourceFactory database = new DataSourceFactory();
}

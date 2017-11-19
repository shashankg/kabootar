package com.shash.kabootar.templatizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shash.kabootar.commons.dropwizard.AppException;
import com.shash.kabootar.templatizer.domain.Template;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

/**
 * @author shashankgautam
 */
@Converter
@Slf4j
public class TemplateConverter implements AttributeConverter<Template, String> {

    private final ObjectMapper mapper;

    public TemplateConverter() {
        this(new ObjectMapper());
    }

    public TemplateConverter(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String convertToDatabaseColumn(final Template object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            log.error("Unable to parse component source - {}", e.getMessage(), e);
            throw new AppException(e);
        }
    }

    @Override
    public Template convertToEntityAttribute(final String source) {
        try {
            return mapper.readValue(source, Template.class);
        } catch (final IOException e) {
            log.error("Unable to parse ExecutorConfig source - {}", e.getMessage(), e);
            throw new AppException(e);
        }
    }
}
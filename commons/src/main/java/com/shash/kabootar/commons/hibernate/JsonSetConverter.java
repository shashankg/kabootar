package com.shash.kabootar.commons.hibernate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shash.kabootar.commons.dropwizard.AppException;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * @author shashankgautam
 */
//@Slf4j
public class JsonSetConverter implements AttributeConverter<Set, String> {

    private final ObjectMapper serializer;

    public JsonSetConverter() {
        this(new ObjectMapper());
    }

    public JsonSetConverter(final ObjectMapper serializer) {
        this.serializer = serializer;
    }

    @Override
    public String convertToDatabaseColumn(final Set value) {
        try {
            return serializer.writeValueAsString(value);
        } catch (final JsonProcessingException e) {
//            log.error("Unable to parse Set - {}. Error - {}", value, e.getMessage(), e);
            throw new AppException(e);
        }
    }

    @Override
    public Set convertToEntityAttribute(final String value) {
        try {
            if (value == null) {
                return Collections.emptySet();
            }
            return serializer.readValue(value, Set.class);
        } catch (final IOException e) {
//            log.error("Unable to parse value to Set - {}. Error - {}", value, e.getMessage(), e);
            throw new AppException(e);
        }
    }
}

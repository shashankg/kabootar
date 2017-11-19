package com.shash.kabootar.commons.hibernate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shash.kabootar.commons.dropwizard.AppException;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * TODO .
 */
@Converter
//@Slf4j
public class JsonListConverter implements AttributeConverter<List, String> {

    private final ObjectMapper serializer;

    public JsonListConverter() {
        this(new ObjectMapper());
    }

    public JsonListConverter(final ObjectMapper serializer) {
        this.serializer = serializer;
    }

    @Override
    public String convertToDatabaseColumn(final List value) {
        try {
            return serializer.writeValueAsString(value);
        } catch (final JsonProcessingException e) {
//            log.error("Unable to parse list - {}. Error - {}", value, e.getMessage(), e);
            throw new AppException(e);
        }
    }

    @Override
    public List convertToEntityAttribute(final String value) {
        try {
            if (value == null) {
                return Collections.emptyList();
            }
            return serializer.readValue(value, List.class);
        } catch (final IOException e) {
//            log.error("Unable to parse value to list - {}. Error - {}", value, e.getMessage(), e);
            throw new AppException(e);
        }
    }
}

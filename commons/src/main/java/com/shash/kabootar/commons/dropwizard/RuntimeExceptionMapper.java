package com.shash.kabootar.commons.dropwizard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.shash.kabootar.commons.exception.ResourceNotFoundException;
import com.shash.kabootar.commons.exception.UnProcessableException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author shashankgautam
 */
//@Slf4j
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    private static final int UN_PROCESSABLE_STATUS = 422;

    @Override
    public Response toResponse(final RuntimeException e) {

        final Response defaultResponse =
                Response.serverError().entity(ImmutableMap.of("errors", ImmutableList.of(e.getMessage()))).build();

//        log.error("Error - {}", e.getMessage());

        if (e instanceof ResourceNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ImmutableMap.of("errors", ImmutableList.of(e.getMessage()))).build();
        } else if (e instanceof ConstraintViolationException) {
            return Response.status(UN_PROCESSABLE_STATUS)
                    .entity(ImmutableMap.of("errors", ImmutableList.of(e.getMessage()))).build();
        } else if (e instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ImmutableMap.of("errors", ImmutableList.of(e.getMessage()))).build();
        } else if (e instanceof UnProcessableException) {
            return Response.status(UN_PROCESSABLE_STATUS)
                    .entity(ImmutableMap.of("errors", ImmutableList.of(e.getMessage()))).build();
        } else if (e instanceof org.hibernate.exception.ConstraintViolationException) {
            return Response.status(UN_PROCESSABLE_STATUS)
                    .entity(ImmutableMap.of("errors", ImmutableList.of(e.getMessage()))).build();
        }

//        log.error("Stack trace - ", e);
        return defaultResponse;
    }
}

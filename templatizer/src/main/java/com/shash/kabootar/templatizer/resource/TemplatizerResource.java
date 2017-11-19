package com.shash.kabootar.templatizer.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableMap;
import com.shash.kabootar.commons.filter.UserRequired;
import com.shash.kabootar.templatizer.domain.Template;
import com.shash.kabootar.templatizer.service.ITemplatizerService;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author shashankgautam
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("/v1/templatizer")
@Slf4j
@AllArgsConstructor
public class TemplatizerResource {

    private ITemplatizerService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    @UnitOfWork(transactional = true)
    @UserRequired
    public Response create(@NotNull final Template template) {
        final Template createdTemplate = service.create(template);
        return Response.status(Response.Status.CREATED).entity(createdTemplate).build();
    }

    @GET
    @Timed
    @UnitOfWork(transactional = false)
    @Path("{id}")
    public Response get(@PathParam("id") final long id) {
        final Template template = service.getById(id);
        return Response.ok().entity(template).build();
    }
}

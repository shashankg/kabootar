package com.shash.kabootar.templatizer.resource;

import com.codahale.metrics.annotation.Timed;
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
    @Path("{tenant}/{name}")
    public Response get(@PathParam("tenant") final String tenant, @PathParam("name") final String name) {
        final Template template = service.get(tenant, name);
        return Response.ok().entity(template).build();
    }

    @PUT
    @Timed
    @UnitOfWork(transactional = true)
    @Path("{tenant}/{name}")
    @UserRequired
    public Response update(@PathParam("tenant") final String tenant, @PathParam("name") final String name, final Template template) {
        service.update(tenant, name, template);
        return Response.noContent().build();
    }

    @PUT
    @Timed
    @UnitOfWork(transactional = true)
    @Path("{tenant}/{name}/publish")
    @UserRequired
    public Response publish(@PathParam("tenant") final String tenant, @PathParam("name") final String name) {
        service.publish(tenant, name);
        return Response.noContent().build();
    }

    @PUT
    @Timed
    @UnitOfWork(transactional = true)
    @Path("{tenant}/{name}/unpublish")
    @UserRequired
    public Response unpublish(@PathParam("tenant") final String tenant, @PathParam("name") final String name) {
        service.unpublish(tenant, name);
        return Response.noContent().build();
    }

    @DELETE
    @Timed
    @UnitOfWork(transactional = true)
    @Path("{tenant}/{name}")
    @UserRequired
    public Response delete(@PathParam("tenant") final String tenant, @PathParam("name") final String name) {
        service.delete(tenant, name);
        return Response.noContent().build();
    }
}

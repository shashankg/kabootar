package com.shash.kabootar.commons.filter;

import com.google.common.base.Strings;
import com.shash.kabootar.commons.exception.UnProcessableException;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * @author shashankgautam
 */
@UserRequired
//@Slf4j
public class UserFilter implements ContainerRequestFilter, ContainerResponseFilter {

    public static final String X_USER = "X-User";

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        final Context context = UserContext.instance().getContextThreadLocal();
        final String userKey = requestContext.getHeaderString(X_USER);
//        log.debug(X_USER + " -  {}", userKey);

        if (Strings.isNullOrEmpty(userKey)) {
//            log.error("X-User: {}, X-User can't be null/empty", userKey);
            throw new UnProcessableException("X-User can't be null/empty, X-USER: " + userKey);
        }
        context.setItem(userKey);
    }

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
            throws IOException {
//        log.trace("Clearing the user context information for the request {}", responseContext);
        UserContext.instance().clear();
    }
}

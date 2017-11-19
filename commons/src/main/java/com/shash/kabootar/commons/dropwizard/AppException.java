package com.shash.kabootar.commons.dropwizard;

/**
 * @author shashankgautam
 */
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MSG = "Something went wrong...";

    /**
     * @param cause to throw
     */
    public AppException(final Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
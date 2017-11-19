package com.shash.kabootar.commons.exception;

/**
 * @author shashankgautam
 */
public class UnProcessableException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MSG = "Unable to process...Something invalid here ?";

    public UnProcessableException() {
        super(DEFAULT_MSG);
    }

    /**
     * @param message to throw
     * @param cause to throw
     */
    public UnProcessableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message to throw
     */
    public UnProcessableException(final String message) {
        super(message);
    }

    /**
     * @param cause to throw
     */
    public UnProcessableException(final Throwable cause) {
        super(DEFAULT_MSG, cause);
    }
}

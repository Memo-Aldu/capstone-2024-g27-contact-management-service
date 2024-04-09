package com.crm.contactmanagementservice.exceptions;

import java.io.Serial;

/**
 * Custom exception class for entity not found scenario.
 * This class extends the RuntimeException class and provides two constructors.
 * One constructor takes a message and an exception, the other only takes a message.
 */
public class AppEntityNotFoundException extends RuntimeException {

    /**
     * Serial version UID for serialization. This is needed because this class extends a Serializable class.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for the AppEntityNotFoundException class.
     * This constructor takes a message and an exception as parameters.
     * @param message The detail message, saved for later retrieval by the Throwable.getMessage() method.
     * @param exception The cause (which is saved for later retrieval by the Throwable.getCause() method).
     */
    public AppEntityNotFoundException(String message, Exception exception) {
        super(message, exception);
    }

    /**
     * Constructor for the AppEntityNotFoundException class.
     * This constructor takes a message as a parameter.
     * @param message The detail message, saved for later retrieval by the Throwable.getMessage() method.
     */
    public AppEntityNotFoundException(String message) {
        super(message);
    }
}
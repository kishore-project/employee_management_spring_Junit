package com.ideas2it.employeemanagement.exceptions;

/**
 * This class use for custom exceptions.
 * Extends RuntimeException to provide more specific error handling.
 * @author kishore
 */
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException() {
        super();
    }

    public ResourceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}

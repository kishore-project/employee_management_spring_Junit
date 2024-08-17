package com.ideas2it.employeemanagement.exceptions;

/**
 * This class use for custom exceptions.
 * Extends RuntimeException to provide more specific error handling.
 * @author kishore
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

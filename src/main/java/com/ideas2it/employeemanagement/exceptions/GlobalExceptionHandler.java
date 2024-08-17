package com.ideas2it.employeemanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler is responsible for handling exceptions that occur throughout the application.
 * This class provides custom error messages and response status codes for different types of exceptions,
 * ensuring a better user experience and easier debugging.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles cases where a resource already exists.
     * This method intercepts ResourceAlreadyExistsException and returns a 409 Conflict status.
     *
     * @param req   The HttpServletRequest object that contains the request the client made to the server.
     * @param e     The exception that was thrown.
     * @return A ResponseEntity containing the error details and HTTP status.
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(HttpServletRequest req, Exception e) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", "Conflict: The resource you're trying to create or update already exists. " +
                "Please check your input and try again.");
        errorDetails.put("details", e.getMessage());
        errorDetails.put("url", req.getRequestURL());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    /**
     * Handles cases where a requested resource could not be found.
     * This method intercepts ResourceNotFoundException and returns a 404 Not Found status.
     *
     * @param req   The HttpServletRequest object that contains the request the client made to the server.
     * @param e     The exception that was thrown.
     * @return A ResponseEntity containing the error details and HTTP status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(HttpServletRequest req, Exception e) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", "Resource Not Found: The resource you're looking for could not be found. " +
                "It may have been moved, deleted, or you may have the wrong ID.");
        errorDetails.put("details", e.getMessage());
        errorDetails.put("url", req.getRequestURL());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles general server errors.
     * This method intercepts RuntimeException and returns a 500 Internal Server Error status.
     *
     * @param req   The HttpServletRequest object that contains the request the client made to the server.
     * @param e     The exception that was thrown.
     * @return A ResponseEntity containing the error details and HTTP status.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleAnyServerError(HttpServletRequest req, Exception e) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", "Internal Server Error: An unexpected error occurred on the server. " +
                "Please try again later or contact support if the issue persists.");
        errorDetails.put("details", e.getMessage());
        errorDetails.put("url", req.getRequestURL());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles cases where an argument fails validation.
     * This method intercepts MethodArgumentNotValidException and returns a map of field errors and their respective messages.
     *
     * @param ex The exception that was thrown when a method argument is invalid.
     * @return A Map containing field names as keys and error messages as values.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex,HttpServletRequest req, Exception e) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
       // errorMap.put("details", e.getMessage());
        errorMap.put("url", String.valueOf(req.getRequestURL()));
       errorMap.put("TimeStamp", String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }


}
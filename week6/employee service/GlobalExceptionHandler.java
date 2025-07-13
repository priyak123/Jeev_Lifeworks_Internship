package com.employee.employeeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Indicates this class handles exceptions globally across the whole application
public class GlobalExceptionHandler {

    // Handles all RuntimeExceptions such as resource not found, illegal arguments, etc.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage()); // Return the exception message in the response
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // HTTP 404 Not Found
    }

    // Handles validation errors triggered by @Valid in controller methods
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Loop through all field errors and collect messages against each field
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage()); // e.g., "name" -> "Name cannot be blank"
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // HTTP 400 Bad Request
    }

    // Catch-all for any other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal server error"); // Generic error message
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // HTTP 500
    }
}

package com.student.studentmanagement.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to manage application-wide exceptions in a centralized way.
 * Handles both custom and validation-related exceptions gracefully.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles custom StudentNotFoundException when a student with a given ID is not found.
     *
     * @param ex the exception thrown
     * @return ResponseEntity with NOT_FOUND (404) status and exception message
     */
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFound(StudentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation errors triggered by @Valid annotations in request DTOs.
     *
     * @param ex the MethodArgumentNotValidException thrown by Spring validation
     * @return a map of field names to corresponding validation error messages with BAD_REQUEST (400) status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Extract field-level validation errors and add to the response map
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

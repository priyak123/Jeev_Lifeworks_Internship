package com.sms.exception;

/**
 * Custom unchecked exception for Student-related errors.
 */
public class StudentException extends RuntimeException {

    // Constructor to pass a custom error message
    public StudentException(String message) {
        super(message);
    }
}

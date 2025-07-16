package com.student.studentmanagement.exception;

/**
 * Custom exception thrown when a student with the specified ID is not found in the system.
 * Extends RuntimeException to be used in service layer without mandatory try-catch blocks.
 */
public class StudentNotFoundException extends RuntimeException {

    /**
     * Constructor that accepts a custom exception message.
     *
     * @param message the error message to be returned when exception is thrown
     */
    public StudentNotFoundException(String message) {
        super(message); // Pass the message to the base RuntimeException class
    }
}

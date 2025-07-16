package com.student.studentmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Data Transfer Object (DTO) for capturing student data during create/update operations.
 * Includes validation annotations to ensure data integrity.
 */
@Data // Lombok: generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Lombok: generates a no-args constructor
@AllArgsConstructor // Lombok: generates an all-args constructor
public class StudentRequestDTO {

    /**
     * Full name of the student.
     * Must not be null or blank (only whitespace is also considered invalid).
     */
    @NotBlank(message = "Name is required")
    private String name;

    /**
     * Age of the student.
     * Must be at least 1 (positive integer).
     */
    @Min(value = 1, message = "Age must be positive")
    private int age;

    /**
     * Grade assigned to the student.
     * Must match one of the valid predefined values like A+, A, B+, B, etc.
     * Ensures consistent grading format using regex.
     */
    @Pattern(regexp = "^(A\\+|A|B\\+|B|C\\+|C|D|F)$", message = "Grade must be valid")
    private String grade;

    /**
     * Optional field representing the student's home address.
     */
    private String address;
}

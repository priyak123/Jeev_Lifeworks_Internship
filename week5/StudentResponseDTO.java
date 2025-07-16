package com.student.studentmanagement.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) used to send student data in API responses.
 * This class represents the final structure returned to the client.
 */
@Data // Lombok: generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Lombok: generates a no-argument constructor
@AllArgsConstructor // Lombok: generates a constructor with all fields
public class StudentResponseDTO {

    /**
     * Unique identifier for the student.
     */
    private Long id;

    /**
     * Name of the student.
     */
    private String name;

    /**
     * Age of the student.
     */
    private int age;

    /**
     * Grade achieved by the student.
     * Typically values like A+, B, etc.
     */
    private String grade;

    /**
     * Address of the student.
     */
    private String address;
}

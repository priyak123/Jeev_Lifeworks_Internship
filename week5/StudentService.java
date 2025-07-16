package com.student.studentmanagement.service;

import java.util.List;

import com.student.studentmanagement.dto.StudentRequestDTO;
import com.student.studentmanagement.dto.StudentResponseDTO;

/**
 * Service interface that defines the business operations for managing students.
 * Acts as a contract for implementing the core logic related to student management.
 */
public interface StudentService {

    /**
     * Adds a new student to the system.
     *
     * @param dto The student data received from the client.
     * @return The created student's response DTO.
     */
    StudentResponseDTO addStudent(StudentRequestDTO dto);

    /**
     * Retrieves all students from the system.
     *
     * @return A list of student response DTOs.
     */
    List<StudentResponseDTO> getAllStudents();

    /**
     * Fetches the details of a specific student by ID.
     *
     * @param id The unique identifier of the student.
     * @return The corresponding student response DTO.
     */
    StudentResponseDTO getStudentById(Long id);

    /**
     * Updates an existing student's information.
     *
     * @param id  The ID of the student to be updated.
     * @param dto The new student data.
     * @return The updated student response DTO.
     */
    StudentResponseDTO updateStudent(Long id, StudentRequestDTO dto);

    /**
     * Deletes a student from the system by ID.
     *
     * @param id The ID of the student to be removed.
     */
    void deleteStudent(Long id);
}

package com.student.studentmanagement.controller;

import com.student.studentmanagement.dto.*;
import com.student.studentmanagement.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling Student-related API endpoints.
 * Supports basic CRUD operations on the student resource.
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    // Inject the StudentService to delegate business logic
    @Autowired
    private StudentService studentService;

    /**
     * Create a new student using POST request.
     *
     * @param dto the request body containing student data
     * @return the created student response with HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<StudentResponseDTO> addStudent(@Valid @RequestBody StudentRequestDTO dto) {
        return new ResponseEntity<>(studentService.addStudent(dto), HttpStatus.CREATED);
    }

    /**
     * Retrieve a list of all students.
     *
     * @return list of StudentResponseDTO with HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * Retrieve a specific student by ID.
     *
     * @param id the student ID
     * @return the student details with HTTP 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    /**
     * Update student details by ID.
     *
     * @param id  the student ID to update
     * @param dto the new student data
     * @return the updated student response
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id,
                                                             @Valid @RequestBody StudentRequestDTO dto) {
        return ResponseEntity.ok(studentService.updateStudent(id, dto));
    }

    /**
     * Delete a student by ID.
     *
     * @param id the student ID to delete
     * @return HTTP 204 No Content if deletion successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

package com.student.studentmanagement.service.impl;

import com.student.studentmanagement.dto.*;
import com.student.studentmanagement.exception.StudentNotFoundException;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.StudentRepository;
import com.student.studentmanagement.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the StudentService interface.
 * Contains business logic for handling student-related operations.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repository;

    /**
     * Utility method to map Student entity to StudentResponseDTO.
     */
    private StudentResponseDTO mapToResponse(Student student) {
        return new StudentResponseDTO(
            student.getId(),
            student.getName(),
            student.getAge(),
            student.getGrade(),
            student.getAddress()
        );
    }

    /**
     * Utility method to map StudentRequestDTO to Student entity.
     */
    private Student mapToEntity(StudentRequestDTO dto) {
        return new Student(null, dto.getName(), dto.getAge(), dto.getGrade(), dto.getAddress());
    }

    /**
     * Adds a new student to the repository.
     */
    @Override
    public StudentResponseDTO addStudent(StudentRequestDTO dto) {
        Student student = repository.save(mapToEntity(dto));
        return mapToResponse(student);
    }

    /**
     * Retrieves all students from the repository.
     */
    @Override
    public List<StudentResponseDTO> getAllStudents() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific student by ID, throws exception if not found.
     */
    @Override
    public StudentResponseDTO getStudentById(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        return mapToResponse(student);
    }

    /**
     * Updates an existing student record by ID.
     */
    @Override
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO dto) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setGrade(dto.getGrade());
        student.setAddress(dto.getAddress());

        return mapToResponse(repository.save(student));
    }

    /**
     * Deletes a student by ID. Throws exception if the student doesn't exist.
     */
    @Override
    public void deleteStudent(Long id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        repository.deleteById(id);
    }
}

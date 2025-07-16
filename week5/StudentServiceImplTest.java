package com.student.studentmangement.service.impl;

import com.student.studentmanagement.dto.StudentRequestDTO;
import com.student.studentmanagement.dto.StudentResponseDTO;
import com.student.studentmanagement.exception.StudentNotFoundException;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.StudentRepository;
import com.student.studentmanagement.service.impl.StudentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StudentServiceImpl using JUnit and Mockito.
 */
public class StudentServiceImplTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentServiceImpl service;

    private Student student;
    private StudentRequestDTO requestDTO;

    /**
     * Set up test data and initialize mocks before each test case.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        student = new Student(1L, "John", 20, "A", "NY");
        requestDTO = new StudentRequestDTO("John", 20, "A", "NY");
    }

    /**
     * Test for adding a student successfully.
     */
    @Test
    void testAddStudent() {
        when(repository.save(any(Student.class))).thenReturn(student);

        StudentResponseDTO result = service.addStudent(requestDTO);

        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals(20, result.getAge());
        verify(repository, times(1)).save(any(Student.class));
    }

    /**
     * Test for retrieving all students.
     */
    @Test
    void testGetAllStudents() {
        List<Student> students = Arrays.asList(
            new Student(1L, "John", 20, "A", "NY"),
            new Student(2L, "Jane", 21, "B", "LA")
        );

        when(repository.findAll()).thenReturn(students);

        List<StudentResponseDTO> results = service.getAllStudents();

        assertEquals(2, results.size());
        assertEquals("Jane", results.get(1).getName());
        verify(repository, times(1)).findAll();
    }

    /**
     * Test fetching student by ID when found.
     */
    @Test
    void testGetStudentById_Found() {
        when(repository.findById(1L)).thenReturn(Optional.of(student));

        StudentResponseDTO result = service.getStudentById(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(repository, times(1)).findById(1L);
    }

    /**
     * Test fetching student by ID when not found (should throw exception).
     */
    @Test
    void testGetStudentById_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(StudentNotFoundException.class, () -> {
            service.getStudentById(99L);
        });

        assertEquals("Student not found with id: 99", exception.getMessage());
        verify(repository, times(1)).findById(99L);
    }

    /**
     * Test updating an existing student successfully.
     */
    @Test
    void testUpdateStudent_Found() {
        Student updatedStudent = new Student(1L, "John Updated", 22, "B+", "Chicago");
        StudentRequestDTO updatedDTO = new StudentRequestDTO("John Updated", 22, "B+", "Chicago");

        when(repository.findById(1L)).thenReturn(Optional.of(student));
        when(repository.save(any(Student.class))).thenReturn(updatedStudent);

        StudentResponseDTO result = service.updateStudent(1L, updatedDTO);

        assertEquals("John Updated", result.getName());
        assertEquals("B+", result.getGrade());
        verify(repository).save(any(Student.class));
    }

    /**
     * Test updating a non-existing student (should throw exception).
     */
    @Test
    void testUpdateStudent_NotFound() {
        when(repository.findById(50L)).thenReturn(Optional.empty());

        StudentRequestDTO dto = new StudentRequestDTO("Fake", 19, "A", "NoCity");

        Exception exception = assertThrows(StudentNotFoundException.class, () -> {
            service.updateStudent(50L, dto);
        });

        assertEquals("Student not found with id: 50", exception.getMessage());
        verify(repository, never()).save(any());
    }

    /**
     * Test deleting an existing student successfully.
     */
    @Test
    void testDeleteStudent_Found() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteStudent(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    /**
     * Test deleting a non-existing student (should throw exception).
     */
    @Test
    void testDeleteStudent_NotFound() {
        when(repository.existsById(200L)).thenReturn(false);

        Exception exception = assertThrows(StudentNotFoundException.class, () -> {
            service.deleteStudent(200L);
        });

        assertEquals("Student not found with id: 200", exception.getMessage());
        verify(repository, never()).deleteById(200L);
    }
}

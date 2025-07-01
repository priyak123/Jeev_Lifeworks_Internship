package com.sms.test;

import com.sms.model.Student;
import com.sms.service.StudentService;
import com.sms.service.StudentServiceImpl;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for testing StudentService implementation.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Enforce test execution order
public class StudentServiceTest {

    private static StudentService service;
    private static int testStudentId; // Holds ID of the student created during test

    @BeforeAll
    public static void init() {
        service = new StudentServiceImpl(); // Initialize service before all tests
    }

    /**
     * Test case to add a student and verify insertion.
     */
    @Test
    @Order(1)
    public void testAddStudent() {
        Student s = new Student("JUnitUser", 22, "junituser@example.com", "Java");
        service.createStudent(s); // Add test student

        // Fetch all students and check if the new one exists
        List<Student> list = service.fetchAllStudents();
        assertTrue(list.stream().anyMatch(stu -> stu.getEmail().equals("junituser@example.com")));

        // Save the test student’s ID for update/delete tests
        testStudentId = list.stream()
                .filter(stu -> stu.getEmail().equals("junituser@example.com"))
                .findFirst()
                .get()
                .getId();
    }

    /**
     * Test to check that students can be fetched.
     */
    @Test
    @Order(2)
    public void testFetchAllStudents() {
        List<Student> students = service.fetchAllStudents();
        assertNotNull(students); // Ensure list is not null
        assertTrue(students.size() > 0); // Ensure list has at least one student
    }

    /**
     * Test to update the previously added student.
     */
    @Test
    @Order(3)
    public void testUpdateStudent() {
        // Create student object with same ID but updated data
        Student updated = new Student("JUnitUpdated", 23, "junituser@example.com", "Spring Boot");
        updated.setId(testStudentId);
        service.updateStudent(updated);

        // Verify that student name was updated
        List<Student> list = service.fetchAllStudents();
        assertTrue(list.stream().anyMatch(stu -> stu.getName().equals("JUnitUpdated")));
    }

    /**
     * Test to delete the test student and verify deletion.
     */
    @Test
    @Order(4)
    public void testDeleteStudent() {
        service.deleteStudent(testStudentId); // Delete student by ID

        // Verify student is no longer in list
        List<Student> list = service.fetchAllStudents();
        assertFalse(list.stream().anyMatch(stu -> stu.getId() == testStudentId));
    }
}

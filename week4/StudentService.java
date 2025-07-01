package com.sms.service;

import com.sms.model.Student;
import java.util.List;

/**
 * Service interface for student operations.
 */
public interface StudentService {

    // Create a new student
    void createStudent(Student student);

    // Retrieve all students
    List<Student> fetchAllStudents();

    // Update an existing student
    void updateStudent(Student student);

    // Delete a student by ID
    void deleteStudent(int id);

    // Export all student data to a CSV file
    void exportToCSV(String filePath);
}

package com.sms.dao;

import com.sms.model.Student;
import java.util.List;

// Interface for student database operations
public interface StudentDAO {

    // Add student to DB
    boolean addStudent(Student student);

    // Get all students from DB
    List<Student> getAllStudents();

    // Update student in DB
    boolean updateStudent(Student student);

    // Delete student by ID from DB
    boolean deleteStudent(int id);
}

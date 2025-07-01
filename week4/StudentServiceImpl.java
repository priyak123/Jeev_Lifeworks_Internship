package com.sms.service;

import com.sms.dao.*;
import com.sms.model.Student;
import com.sms.util.LoggerUtil;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Implementation of StudentService.
 * Handles business logic and interacts with DAO layer.
 */
public class StudentServiceImpl implements StudentService {

    // DAO instance to interact with the database
    private StudentDAO dao = new StudentDaoImpl();

    /**
     * Adds a new student using DAO, logs the operation.
     */
    public void createStudent(Student s) {
        if (dao.addStudent(s)) {
            System.out.println("✅ Student added!");
            LoggerUtil.log("Added: " + s.getName());
        } else {
            System.out.println("❌ Failed to add student.");
        }
    }

    /**
     * Retrieves all students via DAO.
     * @return list of all students
     */
    public List<Student> fetchAllStudents() {
        return dao.getAllStudents();
    }

    /**
     * Updates an existing student's data and logs the result.
     */
    public void updateStudent(Student s) {
        if (dao.updateStudent(s)) {
            System.out.println("✅ Updated successfully!");
            LoggerUtil.log("Updated ID " + s.getId());
        } else {
            System.out.println("❌ Update failed.");
        }
    }

    /**
     * Deletes a student by ID and logs the result.
     */
    public void deleteStudent(int id) {
        if (dao.deleteStudent(id)) {
            System.out.println("✅ Deleted.");
            LoggerUtil.log("Deleted ID " + id);
        } else {
            System.out.println("❌ Deletion failed.");
        }
    }

    /**
     * Exports all student data to a CSV file and logs the export.
     */
    public void exportToCSV(String filePath) {
        List<Student> students = dao.getAllStudents();
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write CSV header
            writer.println("ID,Name,Age,Email,Course");

            // Write student data rows
            students.forEach(s -> writer.println(
                s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getEmail() + "," + s.getCourse()));

            System.out.println("✅ Exported to CSV: " + filePath);
            LoggerUtil.log("Exported CSV to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

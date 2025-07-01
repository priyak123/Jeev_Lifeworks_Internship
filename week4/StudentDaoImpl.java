package com.sms.dao;

import com.sms.db.DBConnection;
import com.sms.model.Student;

import java.sql.*;
import java.util.*;

public class StudentDaoImpl implements StudentDAO {

    // Add a new student to the database
    public boolean addStudent(Student s) {
        String sql = "INSERT INTO students(name, age, email, course) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setInt(2, s.getAge());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getCourse());
            return ps.executeUpdate() > 0; // return true if insert succeeds
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all students from the database
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setAge(rs.getInt("age"));
                s.setEmail(rs.getString("email"));
                s.setCourse(rs.getString("course"));
                list.add(s); // add student to list
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update an existing student
    public boolean updateStudent(Student s) {
        String sql = "UPDATE students SET name=?, age=?, email=?, course=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setInt(2, s.getAge());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getCourse());
            ps.setInt(5, s.getId());
            return ps.executeUpdate() > 0; // return true if update succeeds
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a student by ID
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0; // return true if delete succeeds
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

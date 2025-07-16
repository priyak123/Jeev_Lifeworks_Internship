package com.student.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.student.studentmanagement.model.Student;

/**
 * Repository interface for performing CRUD operations on Student entities.
 * Extends JpaRepository to inherit built-in JPA methods such as save, findById, findAll, deleteById, etc.
 */
@Repository // Indicates this is a Spring-managed Repository bean
public interface StudentRepository extends JpaRepository<Student, Long> {
    // No custom methods needed for now – all CRUD operations are inherited from JpaRepository
}

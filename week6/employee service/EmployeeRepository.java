package com.employee.employeeservice.Repository;

import java.util.List;
import java.util.Optional;

import com.employee.employeeservice.model.Employee;

/**
 * EmployeeRepository defines the contract for data access operations related to Employee.
 * This is a custom interface meant for JDBC or in-memory storage implementations.
 */
public interface EmployeeRepository {

    /**
     * Saves a new employee to the data source.
     *
     * @param employee The employee object to be saved.
     * @return The saved employee object (with possibly generated ID).
     */
    Employee save(Employee employee);

    /**
     * Retrieves all employees from the data source.
     *
     * @return A list of all employees.
     */
    List<Employee> findAll();

    /**
     * Finds an employee by their ID.
     *
     * @param id The ID of the employee to find.
     * @return An Optional containing the employee if found, or empty otherwise.
     */
    Optional<Employee> findById(int id);

    /**
     * Updates the data of an existing employee by ID.
     *
     * @param id       The ID of the employee to update.
     * @param employee The updated employee object.
     * @return The updated employee.
     */
    Employee update(int id, Employee employee);

    /**
     * Deletes an employee by their ID.
     *
     * @param id The ID of the employee to delete.
     */
    void delete(int id);
}

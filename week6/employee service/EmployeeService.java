package com.employee.employeeservice.service;

import java.util.List;

import com.employee.employeeservice.dto.EmployeeRequest;
import com.employee.employeeservice.model.Employee;

/**
 * Service interface for employee-related business logic.
 * Defines all the operations available for managing Employee records.
 */
public interface EmployeeService {

    /**
     * Creates a new employee based on the provided request data.
     *
     * @param request DTO containing employee creation data
     * @return The created Employee object
     */
    Employee createEmployee(EmployeeRequest request);

    /**
     * Retrieves all employees in the system.
     *
     * @return A list of all Employee objects
     */
    List<Employee> getAllEmployees();

    /**
     * Retrieves a specific employee by their ID.
     *
     * @param id The ID of the employee
     * @return The corresponding Employee object
     */
    Employee getEmployeeById(int id);

    /**
     * Updates an existing employee's data based on ID and request payload.
     *
     * @param id The ID of the employee to update
     * @param request DTO containing updated employee data
     * @return The updated Employee object
     */
    Employee updateEmployee(int id, EmployeeRequest request);

    /**
     * Deletes an employee by their ID.
     *
     * @param id The ID of the employee to delete
     */
    void deleteEmployee(int id);
}

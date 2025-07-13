package com.employee.employeeservice.controller;

import jakarta.validation.Valid; // Enables validation on request bodies
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Represents a full HTTP response (status + body)
import org.springframework.web.bind.annotation.*;

import com.employee.employeeservice.dto.EmployeeRequest; // DTO for employee input validation
import com.employee.employeeservice.model.Employee; // Entity/model representing Employee
import com.employee.employeeservice.service.EmployeeService; // Service layer for business logic

import java.util.List;

@RestController // Marks this class as a REST controller
@RequestMapping("/employees") // All endpoints will be prefixed with /employees
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService; // Injects the EmployeeService bean

    // POST /employees - Create a new employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        // @Valid ensures the request body follows validation rules defined in EmployeeRequest
        // @RequestBody maps the incoming JSON to the EmployeeRequest DTO
        return ResponseEntity.ok(employeeService.createEmployee(request)); // Returns 200 OK with created Employee
    }

    // GET /employees - Retrieve all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        // Calls the service to fetch all employees and returns them as a list
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // GET /employees/{id} - Retrieve an employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        // @PathVariable extracts 'id' from the URL path
        return ResponseEntity.ok(employeeService.getEmployeeById(id)); // Returns 200 OK with employee data
    }

    // PUT /employees/{id} - Update an existing employee by ID
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable int id, // Employee ID to update
            @Valid @RequestBody EmployeeRequest request) { // Validates incoming data
        // Calls the service to update the employee with new data
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    // DELETE /employees/{id} - Delete an employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        // Calls the service to delete the employee
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted with id: " + id); // Returns success message
    }
}

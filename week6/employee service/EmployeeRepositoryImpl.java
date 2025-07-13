package com.employee.employeeservice.Repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.employee.employeeservice.Repository.EmployeeRepository;
import com.employee.employeeservice.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository // Marks this class as a Spring-managed repository bean
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate; // Used to execute SQL queries and updates

    // RowMapper maps rows of a ResultSet to Employee objects
    private RowMapper<Employee> rowMapper = new RowMapper<Employee>() {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Maps each column of the result set to the Employee constructor
            return new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("department"),
                rs.getString("position"),
                rs.getDouble("salary")
            );
        }
    };

    @Override
    public Employee save(Employee emp) {
        // Inserts a new employee record into the database
        jdbcTemplate.update(
            "INSERT INTO employee (name, department, position, salary) VALUES (?, ?, ?, ?)",
            emp.getName(), emp.getDepartment(), emp.getPosition(), emp.getSalary()
        );
        // Note: ID is auto-generated and not retrieved here. Consider using KeyHolder for that.
        return emp;
    }

    @Override
    public List<Employee> findAll() {
        // Executes a SELECT query to fetch all employee records
        return jdbcTemplate.query("SELECT * FROM employee", rowMapper);
    }

    @Override
    public Optional<Employee> findById(int id) {
        // Executes a SELECT query to find an employee by ID
        List<Employee> result = jdbcTemplate.query(
            "SELECT * FROM employee WHERE id = ?",
            rowMapper, id
        );
        // Returns an Optional<Employee> to handle the case when no employee is found
        return result.stream().findFirst();
    }

    @Override
    public Employee update(int id, Employee emp) {
        // Updates the existing employee record based on ID
        jdbcTemplate.update(
            "UPDATE employee SET name = ?, department = ?, position = ?, salary = ? WHERE id = ?",
            emp.getName(), emp.getDepartment(), emp.getPosition(), emp.getSalary(), id
        );
        return emp;
    }

    @Override
    public void delete(int id) {
        // Deletes an employee record by ID
        jdbcTemplate.update("DELETE FROM employee WHERE id = ?", id);
    }
}

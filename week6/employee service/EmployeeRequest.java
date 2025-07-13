package com.employee.employeeservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) used to receive and validate employee data from the client.
 * Contains validation annotations to ensure correct input format.
 */
public class EmployeeRequest {

    @NotBlank(message = "Name cannot be blank") // Ensures name is not null or empty
    private String name;

    @NotBlank(message = "Department cannot be blank") // Ensures department is not null or empty
    private String department;

    @NotBlank(message = "Position cannot be blank") // Ensures position is not null or empty
    private String position;

    @Min(value = 0, message = "Salary must be positive") // Ensures salary is 0 or greater
    private double salary;

    // Getter for 'name'
    public String getName() {
        return name;
    }

    // Setter for 'name'
    public void setName(String name) {
        this.name = name;
    }

    // Getter for 'department'
    public String getDepartment() {
        return department;
    }

    // Setter for 'department'
    public void setDepartment(String department) {
        this.department = department;
    }

    // Getter for 'position'
    public String getPosition() {
        return position;
    }

    // Setter for 'position'
    public void setPosition(String position) {
        this.position = position;
    }

    // Getter for 'salary'
    public double getSalary() {
        return salary;
    }

    // Setter for 'salary'
    public void setSalary(double salary) {
        this.salary = salary;
    }
}

package com.employee.employeeservice.model;

/**
 * Employee model class representing the structure of an employee object.
 * This class is used for in-memory or JDBC-based employee data representation.
 */
public class Employee {

    // Unique identifier for the employee
    private int id;

    // Name of the employee
    private String name;

    // Department to which the employee belongs
    private String department;

    // Job position or title of the employee
    private String position;

    // Salary of the employee
    private double salary;

    // Default no-argument constructor (required for serialization/deserialization)
    public Employee() {}

    // Parameterized constructor to create an Employee object quickly
    public Employee(int id, String name, String department, String position, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.position = position;
        this.salary = salary;
    }

    // Getter for 'id'
    public int getId() {
        return id;
    }

    // Setter for 'id'
    public void setId(int id) {
        this.id = id;
    }

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

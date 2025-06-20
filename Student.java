package week3;

import java.io.Serializable;
import java.util.Objects;

// Student class represents zz student data
public class Student implements Serializable, Comparable<Student> {
    private int id;
    private String name;
    private int age;
    private String grade;
    private String address;

    // Constructor to initialize a student
    public Student(int id, String name, int age, String grade, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.address = address;
    }

    // Getters and Setters for each field
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // Custom string representation of the student object
    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Age: %d | Grade: %s | Address: %s",
                id, name, age, grade, address);
    }

    // Equals based on student ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    // Hash code based on student ID
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Used for sorting in TreeSet (by name here)
    @Override
    public int compareTo(Student o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
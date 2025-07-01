package com.sms.model;

/**
 * Model class representing a Student entity.
 */
public class Student {
	private int id;           // Unique student ID
	private String name;      // Student's name
	private int age;          // Student's age
	private String email;     // Student's email
	private String course;    // Course enrolled

	// Default constructor
	public Student() {
	}

	// Parameterized constructor (excluding ID, which is auto-generated)
	public Student(String name, int age, String email, String course) {
		this.name = name;
		this.age = age;
		this.email = email;
		this.course = course;
	}

	// Getter and Setter for ID
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// Getter and Setter for Name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Getter and Setter for Age
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	// Getter and Setter for Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// Getter and Setter for Course
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	// Overridden toString method for easy display
	@Override
	public String toString() {
		return id + " | " + name + " | " + age + " | " + email + " | " + course;
	}
}

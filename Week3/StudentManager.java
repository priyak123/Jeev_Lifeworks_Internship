package week3;

import java.io.*;
import java.util.*;

// Manages student records and handles file operations
public class StudentManager {
	private ArrayList<Student> students = new ArrayList<>();
	private HashMap<Integer, Student> studentMap = new HashMap<>();
	private TreeSet<Student> sortedStudents = new TreeSet<>();

	// Adds a student after checking ID uniqueness
	public void addStudent(Student student) {
		if (studentMap.containsKey(student.getId())) {
			System.out.println("Error: ID already exists.");
			return; // Exit the Method if already exists
		}
		students.add(student);
		studentMap.put(student.getId(), student);
		sortedStudents.add(student);
		System.out.println("Student added successfully.");
	}

	// Removes a student by ID
	public void removeStudent(int id) {
		Student student = studentMap.get(id);
		if (student == null) {
			System.out.println("Student not found.");
			return;
		}
		students.remove(student);
		studentMap.remove(id);
		sortedStudents.remove(student);
		System.out.println("Student removed successfully.");
	}

	// Updates student details using ID
	public void updateStudent(int id, String name, int age, String grade, String address) {
		Student student = studentMap.get(id);
		if (student == null) {
			System.out.println("Student not found.");
			return;
		}
		sortedStudents.remove(student); // Remove old entry for re-insertion
		student.setName(name);
		student.setAge(age);
		student.setGrade(grade);
		student.setAddress(address);
		sortedStudents.add(student); // Reinsert with updated data
		System.out.println("Student updated successfully.");
	}

	// Searches for a student by ID
	public Student searchStudent(int id) {
		return studentMap.get(id);
	}

	// Displays all students sorted by name
	public void displayAllStudents() {
		if (sortedStudents.isEmpty()) {
			System.out.println("No students available.");
		} else {
			sortedStudents.forEach(System.out::println);
		}
	}

	// Loads student list from file using ObjectInputStream
	public void loadFromFile(String filename) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
			students = (ArrayList<Student>) ois.readObject();
			// Refresh HashMap and TreeSet
			studentMap.clear();
			sortedStudents.clear();
			for (Student s : students) {
				studentMap.put(s.getId(), s);
				sortedStudents.add(s);
			}
			System.out.println("Data loaded from file.");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("File load failed: " + e.getMessage());
		}
	}

	// Saves student list to file using ObjectOutputStream
	public void saveToFile(String filename) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
			oos.writeObject(students);
			System.out.println("Data saved successfully.");
		} catch (IOException e) {
			System.out.println("File save failed: " + e.getMessage());
		}
	}
}
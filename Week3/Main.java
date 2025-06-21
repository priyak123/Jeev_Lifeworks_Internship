package week3;

import java.util.Scanner;

//Main class to provide a menu-driven interface
public class Main {
	private static final String FILENAME = "students.dat";

	public static void main(String[] args) {
		StudentManager manager = new StudentManager();
		manager.loadFromFile(FILENAME); // Load existing data if available
		Scanner sc = new Scanner(System.in);
		int choice;

		// Loop until user chooses to exit
		do {
			// Menu options
			System.out.println("\nStudent Management System:");
			System.out.println("1. Add a new student");
			System.out.println("2. Remove a student by ID");
			System.out.println("3. Update student details by ID");
			System.out.println("4. Search for a student by ID");
			System.out.println("5. Display all students (sorted)");
			System.out.println("6. Exit and save data");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			sc.nextLine(); // Consume leftover newline

			switch (choice) {
			case 1 -> {
				// Collect student details
				System.out.print("Enter ID: ");
				int id = sc.nextInt();
				sc.nextLine();
				System.out.print("Enter Name: ");
				String name = sc.nextLine();
				System.out.print("Enter Age: ");
				int age = sc.nextInt();
				sc.nextLine();
				System.out.print("Enter Grade: ");
				String grade = sc.nextLine();
				System.out.print("Enter Address: ");
				String address = sc.nextLine();

				// Validate inputs
				if (id <= 0 || age <= 0 || name.isEmpty() || address.isEmpty()) {
					System.out.println("Invalid input. Try again.");
				} else {
					manager.addStudent(new Student(id, name, age, grade, address));
				}
			}

			case 2 -> {
				// Remove student
				System.out.print("Enter ID to remove: ");
				int id = sc.nextInt();
				manager.removeStudent(id);
			}

			case 3 -> {
				// Update student
				System.out.print("Enter ID to update: ");
				int id = sc.nextInt();
				sc.nextLine();
				System.out.print("New Name: ");
				String name = sc.nextLine();
				System.out.print("New Age: ");
				int age = sc.nextInt();
				sc.nextLine();
				System.out.print("New Grade: ");
				String grade = sc.nextLine();
				System.out.print("New Address: ");
				String address = sc.nextLine();
				manager.updateStudent(id, name, age, grade, address);
			}

			case 4 -> {
				// Search student
				System.out.print("Enter ID to search: ");
				int id = sc.nextInt();
				Student s = manager.searchStudent(id);
				if (s != null) {
					System.out.println(s);
				} else {
					System.out.println("Student not found.");
				}
			}

			case 5 -> manager.displayAllStudents(); // Display all sorted students

			case 6 -> {
				manager.saveToFile(FILENAME); // Save on exit
				System.out.println("Exiting...");
			}

			default -> System.out.println("Invalid choice.");
			}
		} while (choice != 6);
		sc.close();
	}
}

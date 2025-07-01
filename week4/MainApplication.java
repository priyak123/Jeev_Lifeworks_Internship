package com.sms.main;

import com.sms.model.Student;
import com.sms.service.*;

import java.util.List;
import java.util.Scanner;

public class MainApplication {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // For user input
        StudentService service = new StudentServiceImpl(); // Service layer object

        // Main menu loop
        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Export to CSV");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt(); sc.nextLine(); // Read menu choice

            switch (choice) {
                case 1: // Add student
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Age: ");
                    int age = sc.nextInt(); sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Course: ");
                    String course = sc.nextLine();

                    Student s = new Student(name, age, email, course);
                    service.createStudent(s); // Call service to add
                    break;

                case 2: // View all students
                    List<Student> list = service.fetchAllStudents();
                    list.stream().forEach(System.out::println); // Print each student
                    break;

                case 3: // Update student
                    System.out.print("ID: ");
                    int idU = sc.nextInt(); sc.nextLine();

                    System.out.print("Name: ");
                    String nameU = sc.nextLine();

                    System.out.print("Age: ");
                    int ageU = sc.nextInt(); sc.nextLine();

                    System.out.print("Email: ");
                    String emailU = sc.nextLine();

                    System.out.print("Course: ");
                    String courseU = sc.nextLine();

                    Student upd = new Student(nameU, ageU, emailU, courseU);
                    upd.setId(idU);
                    service.updateStudent(upd); // Call service to update
                    break;

                case 4: // Delete student
                    System.out.print("ID to delete: ");
                    int idD = sc.nextInt();
                    service.deleteStudent(idD); // Call service to delete
                    break;

                case 5: // Export student data to CSV file
                    service.exportToCSV("students.csv");
                    break;

                case 6: // Exit the application
                    System.out.println("👋 Exiting...");
                    return;

                default: // Invalid option
                    System.out.println("❗ Invalid option.");
            }
        }
    }
}

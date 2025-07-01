package com.sms.util;

import com.sms.model.Student;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Utility class for exporting student data to a CSV file.
 */
public class CSVExporter {

    /**
     * Exports a list of students to a CSV file.
     *
     * @param students List of Student objects to be exported.
     * @param filename The name of the CSV file to write to.
     */
    public static void exportToCSV(List<Student> students, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write CSV header
            writer.write("ID,Name,Age,Email,Course\n");

            // Write each student's data as a new line
            for (Student s : students) {
                writer.write(
                    s.getId() + "," +
                    s.getName() + "," +
                    s.getAge() + "," +
                    s.getEmail() + "," +
                    s.getCourse() + "\n"
                );
            }

            System.out.println("Data exported to " + filename);
        } catch (IOException e) {
            // Handle file writing error
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }
}

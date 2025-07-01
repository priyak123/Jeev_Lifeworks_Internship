package com.sms.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for logging messages to a log file.
 */
public class LoggerUtil {

    // Path to the log file
    private static final String LOG_FILE = "logs/app.log";

    /**
     * Writes a log message to the log file.
     * Automatically creates the log directory if it doesn't exist.
     *
     * @param message The message to be logged.
     */
    public static void log(String message) {
        try {
            // Ensure the 'logs' directory exists
            File logDir = new File("logs");
            if (!logDir.exists()) logDir.mkdirs();

            // Open the log file in append mode
            FileWriter fw = new FileWriter(LOG_FILE, true);
            fw.write(message + "\n"); // Write the log message
            fw.close(); // Close writer
        } catch (IOException e) {
            e.printStackTrace(); // Print error if logging fails
        }
    }
}

package com.sms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.*;

public class DBConnection {

    // Path to configuration file that contains DB credentials
    private static final String CONFIG_FILE = "resources/config.properties";

    // DB connection parameters
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    // Static block to load database configuration from config.properties file
    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            Properties props = new Properties();
            props.load(fis); // Load properties from the file
            URL = props.getProperty("db.url"); // Get DB URL
            USER = props.getProperty("db.username"); // Get DB username
            PASSWORD = props.getProperty("db.password"); // Get DB password
        } catch (IOException e) {
            e.printStackTrace(); // Log if loading properties fails
        }
    }

    // Provides a Connection object using the loaded DB credentials
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

package com.user.userservice.model;

/**
 * User model class representing the structure of a user entity.
 * This class is typically used to store user data in memory or a database.
 */
public class User {

    // Unique identifier for the user (e.g., primary key in the database)
    private Long id;

    // Username used for authentication (must be unique)
    private String username;

    // Password for authentication (should be securely hashed in practice)
    private String password;

    // Email address of the user
    private String email;

    /**
     * Default no-argument constructor.
     * Required for frameworks that use reflection (e.g., Jackson, Hibernate).
     */
    public User() {}

    /**
     * Parameterized constructor to create a User object with all fields.
     *
     * @param id       Unique user ID
     * @param username Username for login
     * @param password User password
     * @param email    User email
     */
    public User(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getter for 'id'
    public Long getId() {
        return id;
    }

    // Setter for 'id'
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for 'username'
    public String getUsername() {
        return username;
    }

    // Setter for 'username'
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for 'password'
    public String getPassword() {
        return password;
    }

    // Setter for 'password'
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for 'email'
    public String getEmail() {
        return email;
    }

    // Setter for 'email'
    public void setEmail(String email) {
        this.email = email;
    }
}

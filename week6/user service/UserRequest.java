package com.user.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) used to receive user registration data.
 * Includes validation annotations to ensure input integrity.
 */
public class UserRequest {

    // Username must not be blank (validated during registration request)
    @NotBlank(message = "Username is required")
    private String username;

    // Password must not be blank (validated during registration request)
    @NotBlank(message = "Password is required")
    private String password;

    // Email must be in valid email format (e.g., user@example.com)
    @Email(message = "Email must be valid")
    private String email;

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

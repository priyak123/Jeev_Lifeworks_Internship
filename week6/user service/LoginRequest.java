package com.user.userservice.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) used for capturing login request data.
 * This class receives the username and password from the client.
 */
public class LoginRequest {

    // Username must not be blank (validated automatically by Spring using @Valid)
    @NotBlank(message = "Username is required")
    private String username;

    // Password must not be blank (validated automatically by Spring using @Valid)
    @NotBlank(message = "Password is required")
    private String password;

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
}

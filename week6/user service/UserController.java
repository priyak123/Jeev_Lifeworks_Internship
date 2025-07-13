package com.user.userservice.controller;

import com.user.userservice.dto.JwtResponse;
import com.user.userservice.dto.LoginRequest;
import com.user.userservice.dto.UserRequest;
import com.user.userservice.model.User;
import com.user.userservice.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Marks this class as a REST controller for handling HTTP requests
@RequestMapping("/users") // Base URL path for all endpoints in this controller
public class UserController {

    private final UserService userService;

    // Constructor-based dependency injection for UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user with the provided username and password.
     *
     * @param request User registration data (validated using @Valid)
     * @return ResponseEntity with success message and HTTP 201 status
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRequest request) {
        userService.registerUser(request); // Delegates registration logic to service
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    /**
     * Authenticates a user and returns a JWT token if credentials are valid.
     *
     * @param request Login credentials (username and password)
     * @return JWT token wrapped in a JwtResponse object
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = userService.login(request); // Delegates authentication logic to service
        return ResponseEntity.ok(new JwtResponse(token)); // Returns the token in response body
    }

    /**
     * Retrieves the user's profile using the JWT token from the Authorization header.
     *
     * @param authHeader The Authorization header containing the Bearer token
     * @return User profile information if token is valid
     */
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        // Validate Authorization header format
        if (!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Return 400 if token is malformed
        }

        String token = authHeader.substring(7); // Extract JWT token from header
        User user = userService.getProfile(token); // Get user details from token
        return ResponseEntity.ok(user); // Return user data in the response
    }
}

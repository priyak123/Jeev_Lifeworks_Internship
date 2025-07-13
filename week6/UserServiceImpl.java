package com.user.userservice.service.impl;

import com.user.userservice.dto.LoginRequest;
import com.user.userservice.dto.UserRequest;
import com.user.userservice.model.User;
import com.user.userservice.repository.UserRepository;
import com.user.userservice.security.JwtUtil;
import com.user.userservice.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implementation of the UserService interface.
 * Handles user registration, authentication (login), and profile retrieval based on JWT.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    // Constructor-based dependency injection for repository and JWT utility
    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registers a new user. Checks for username uniqueness before saving.
     *
     * @param request UserRequest DTO containing username, password, and email
     */
    @Override
    public void registerUser(UserRequest request) {
        // Check if the username is already taken
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create new User object
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // ⚠ Password stored in plain text (for demo only)
        user.setEmail(request.getEmail());

        // Save user to repository
        userRepository.save(user);
        LOGGER.info("User registered successfully: {}", user.getUsername());
    }

    /**
     * Authenticates a user and returns a JWT token if credentials are valid.
     *
     * @param request LoginRequest DTO containing username and password
     * @return a signed JWT token
     */
    @Override
    public String login(LoginRequest request) {
        // Find user by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Compare plain-text passwords (⚠ for demo only — should hash in real apps)
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());
        LOGGER.info("Token generated for user: {}", user.getUsername());
        return token;
    }

    /**
     * Retrieves a user profile using a valid JWT token.
     *
     * @param token the JWT token from Authorization header
     * @return User object associated with the token
     */
    @Override
    public User getProfile(String token) {
        // Extract username from token
        String username = jwtUtil.validateTokenAndGetUsername(token);
        LOGGER.info("Extracted username from token: {}", username);

        // Retrieve user by username
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

package com.user.userservice.service;

import com.user.userservice.dto.LoginRequest;
import com.user.userservice.dto.UserRequest;
import com.user.userservice.model.User;

/**
 * Service interface for managing user-related operations such as
 * registration, login (JWT generation), and profile retrieval.
 */
public interface UserService {

    /**
     * Registers a new user using the provided user request data.
     * 
     * @param request DTO containing username, password, and email
     */
    void registerUser(UserRequest request);

    /**
     * Authenticates the user with the provided login credentials and
     * generates a JWT token upon successful login.
     *
     * @param request DTO containing username and password
     * @return a signed JWT token if credentials are valid
     */
    String login(LoginRequest request);

    /**
     * Retrieves the user profile based on the provided JWT token.
     * 
     * @param token the JWT token extracted from the Authorization header
     * @return User object representing the authenticated user
     */
    User getProfile(String token);
}

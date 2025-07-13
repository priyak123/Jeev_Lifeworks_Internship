package com.user.userservice.dto;

/**
 * DTO (Data Transfer Object) for sending a JWT token back to the client after successful login.
 */
public class JwtResponse {

    // The JWT token generated after successful authentication
    private String token;

    /**
     * Constructor to initialize JwtResponse with a token.
     *
     * @param token The JWT token string
     */
    public JwtResponse(String token) {
        this.token = token;
    }

    /**
     * Getter for the token field.
     *
     * @return The JWT token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter for the token field.
     *
     * @param token The JWT token string to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}

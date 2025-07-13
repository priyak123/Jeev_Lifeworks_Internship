package com.user.userservice.repository;

import com.user.userservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Repository class to manage User data using an in-memory store (HashMap).
 * Simulates basic CRUD operations without using a database.
 */
@Repository
public class UserRepository {

    // In-memory user storage where key = username, value = User object
    private final Map<String, User> userStore = new HashMap<>();

    // Counter to simulate auto-incremented user IDs
    private Long userIdCounter = 1L;

    /**
     * Saves a new user to the in-memory store.
     * Assigns an auto-incremented ID before storing.
     *
     * @param user The user object to save
     * @return The saved user object with assigned ID
     */
    public User save(User user) {
        user.setId(userIdCounter++);                  // Auto-generate user ID
        userStore.put(user.getUsername(), user);      // Store by username
        return user;
    }

    /**
     * Retrieves a user from the store by username.
     *
     * @param username The username to look up
     * @return Optional containing User if found, or empty if not
     */
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userStore.get(username));
    }

    /**
     * Returns a list of all users in the store.
     * Useful for testing or debugging.
     *
     * @return List of all stored users
     */
    public List<User> findAll() {
        return new ArrayList<>(userStore.values());
    }
}

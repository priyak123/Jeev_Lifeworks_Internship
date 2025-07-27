import { Injectable } from '@angular/core';

// User interface defines the structure of a user object
interface User {
  fullName: string;
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root' // Makes this service globally available across the app
})
export class AuthService {
  private users: User[] = []; // Local array to hold newly registered users (in-memory until refreshed)

  // Method to register a new user
  register(user: User): boolean {
    // Check if the user with the same email already exists
    const exists = this.users.find(u => u.email === user.email);
    if (exists) {
      return false; // Registration fails if email is already registered
    }

    // Add the new user to local array
    this.users.push(user);

    // Save users to localStorage so they're persisted
    localStorage.setItem('users', JSON.stringify(this.users));
    return true;
  }

  // Method to log in a user
  login(email: string, password: string): boolean {
    // Get all registered users from localStorage
    const storedUsers = JSON.parse(localStorage.getItem('users') || '[]');

    // Try to find a user with matching email and password
    const matchedUser = storedUsers.find((u: User) => u.email === email && u.password === password);

    if (matchedUser) {
      // Store the logged-in user's details in localStorage
      localStorage.setItem('loggedInUser', JSON.stringify(matchedUser));
      return true;
    }

    // Login failed
    return false;
  }

  // Method to log out the user
  logout() {
    // Remove the logged-in user's details from localStorage
    localStorage.removeItem('loggedInUser');
  }

  // Check if any user is currently logged in
  isAuthenticated(): boolean {
    // Returns true if 'loggedInUser' key exists in localStorage
    return !!localStorage.getItem('loggedInUser');
  }

  // Get currently logged-in user's info (if any)
  getCurrentUser(): User | null {
    const user = localStorage.getItem('loggedInUser');
    return user ? JSON.parse(user) : null;
  }
}

// Import necessary Angular core and form modules
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

// Import custom AuthService to handle login logic
import { AuthService } from '../../services/auth.service';

@Component({
  standalone: false, // Indicates this is not a standalone component
  selector: 'app-login', // Component selector used in HTML
  templateUrl: './login.component.html', // Template for the component
  styleUrls: ['./login.component.css'] // Associated styles
})
export class LoginComponent {
  loginForm: FormGroup; // Reactive form group for login
  submitted = false; // Tracks if the form has been submitted
  errorMessage = ''; // Stores error message if login fails

  // Constructor injects necessary services
  constructor(
    private fb: FormBuilder,      // Used to build the reactive form
    private authService: AuthService, // Handles authentication logic
    private router: Router        // Used for navigation on successful login
  ) {
    // Initialize the form with validation rules
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]], // Email required + must be valid format
      password: ['', Validators.required] // Password is required
    });
  }

  // Getter for easy access to form controls in the template
  get f() {
    return this.loginForm.controls;
  }

  // Function called when the login form is submitted
  onSubmit() {
    this.submitted = true; // Mark form as submitted
    this.errorMessage = ''; // Clear previous error message

    // Stop if the form is invalid (i.e., failed validation)
    if (this.loginForm.invalid) return;

    // Extract email and password values from the form
    const { email, password } = this.loginForm.value;

    // Attempt login using AuthService
    const success = this.authService.login(email, password);

    if (success) {
      // Navigate to /todo page if login is successful
      this.router.navigate(['/todo']);
    } else {
      // Show error message if login fails
      this.errorMessage = 'Invalid email or password.';
    }
  }
}

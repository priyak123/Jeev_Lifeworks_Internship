// Import required Angular modules and services
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  standalone: false, // Not a standalone component (belongs to a module)
  selector: 'app-signup', // HTML tag to use this component
  templateUrl: './signup.component.html', // Template file for this component
  styleUrls: ['./signup.component.css'] // Styles specific to this component
})
export class SignupComponent {
  signupForm: FormGroup; // Reactive form group for the signup form
  submitted = false; // Flag to indicate form submission
  errorMessage = ''; // Holds any error messages to display

  // Inject dependencies: FormBuilder for form creation, AuthService for logic, Router for navigation
  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    // Initialize the signup form with form controls and validators
    this.signupForm = this.fb.group({
      fullName: ['', Validators.required], // Full name is required
      email: ['', [Validators.required, Validators.email]], // Email is required and must be valid
      password: ['', [Validators.required, Validators.minLength(6)]], // Password must be at least 6 characters
      confirmPassword: ['', Validators.required] // Confirm password is required
    });
  }

  // Getter method to access form controls in the template
  get f() {
    return this.signupForm.controls;
  }

  // Function called on form submit
  onSubmit() {
    this.submitted = true; // Mark form as submitted

    // Stop submission if form is invalid
    if (this.signupForm.invalid) return;

    // Destructure form values
    const { fullName, email, password, confirmPassword } = this.signupForm.value;

    // Check if passwords match
    if (password !== confirmPassword) {
      this.errorMessage = "Passwords do not match.";
      return;
    }

    // Attempt to register the user using the AuthService
    const isRegistered = this.authService.register({ fullName, email, password });

    // If registration failed (e.g., email already exists)
    if (!isRegistered) {
      this.errorMessage = "Email already exists.";
    } else {
      // If successful, notify and redirect to login
      alert("Signup successful! Please login.");
      this.router.navigate(['/login']);
    }
  }
}

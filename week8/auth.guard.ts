// Import necessary Angular core and routing modules
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root' // Makes this guard available throughout the app
})
export class AuthGuard implements CanActivate {
  // Inject AuthService to check authentication, and Router for navigation
  constructor(private authService: AuthService, private router: Router) {}

  // canActivate is called before navigating to a route protected by this guard
  canActivate(): boolean {
    // Allow navigation only if the user is authenticated
    if (this.authService.isAuthenticated()) {
      return true;
    }

    // If not authenticated, redirect to login page
    this.router.navigate(['/login']);
    return false;
  }
}

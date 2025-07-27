// Import core Angular modules for routing
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Import components for each route
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { TodoComponent } from './components/todo/todo.component';

// Import the AuthGuard to protect routes
import { AuthGuard } from './guards/auth.guard';

// Define application routes
const routes: Routes = [
  // Redirect default path to /login
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  // Public login route
  { path: 'login', component: LoginComponent },

  // Public signup route
  { path: 'signup', component: SignupComponent },

  // Protected to-do route, accessible only if authenticated
  { path: 'todo', component: TodoComponent, canActivate: [AuthGuard] },

  // Wildcard route to handle undefined URLs (redirect to login)
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  // Configure the router with the route definitions
  imports: [RouterModule.forRoot(routes)],

  // Export RouterModule so it's available in AppModule
  exports: [RouterModule]
})
export class AppRoutingModule { }

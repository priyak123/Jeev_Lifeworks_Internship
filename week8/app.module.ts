// Core Angular modules
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

// Routing module for managing app navigation
import { AppRoutingModule } from './app-routing.module';

// Root component of the application
import { AppComponent } from './app.component'; // ✅ Main/root component

// Feature components
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { TodoComponent } from './components/todo/todo.component';

// Services and guards
import { AuthService } from './services/auth.service'; // Handles login, signup, session
import { AuthGuard } from './guards/auth.guard';       // Protects routes from unauthenticated access

@NgModule({
  // Declare all components that belong to this module
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    TodoComponent
  ],

  // Import other modules required for the app to function
  imports: [
    BrowserModule,        // Required for running app in the browser
    AppRoutingModule,     // Handles routing/navigation
    ReactiveFormsModule,  // For reactive form handling
    FormsModule           // (Optional) if using template-driven forms anywhere
  ],

  // Provide services and guards that are injectable across the app
  providers: [AuthService, AuthGuard],

  // Bootstrap the root component (entry point of the app)
  bootstrap: [AppComponent]
})
export class AppModule { }

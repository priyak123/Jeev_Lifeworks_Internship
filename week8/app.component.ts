// Import Angular's core component decorator
import { Component } from '@angular/core';

@Component({
  standalone: false, // This component is not standalone; it belongs to a module
  selector: 'app-root', // HTML tag used to render this component (usually in index.html)
  templateUrl: './app.component.html', // HTML template for the root component
  styleUrls: ['./app.component.css'] // CSS styles specific to this component
})
export class AppComponent {
  // Title property used for binding in template (optional)
  title = 'angular-todo-app';
}

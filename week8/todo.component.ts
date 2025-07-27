// Angular core and form modules
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

// Task model/interface
interface Task {
  title: string;
  description?: string;
  dueDate?: string;
  completed: boolean;
}

@Component({
  standalone: false,                      // Not a standalone component
  selector: 'app-todo',                   // Selector for usage in HTML
  templateUrl: './todo.component.html',   // External template
  styleUrls: ['./todo.component.css']     // External stylesheet
})
export class TodoComponent implements OnInit {
  taskForm: FormGroup;       // Reactive form group for task entry
  tasks: Task[] = [];        // Task list (stored in memory & localStorage)
  submitted = false;         // Used to trigger form validation visuals

  // Search & filter state
  searchText = '';           // Holds current search query string
  filterType: 'all' | 'pending' | 'completed' = 'all';  // Type of filter selected

  constructor(
    private fb: FormBuilder,         // Angular FormBuilder to create form
    private authService: AuthService, // Handles logout and authentication
    private router: Router           // Angular Router for redirection
  ) {
    // Initializing the taskForm with required validators
    this.taskForm = this.fb.group({
      title: ['', Validators.required], // Task title is required
      description: [''],                // Description is optional
      dueDate: ['']                     // Due date is optional
    });
  }

  // Runs when component loads
  ngOnInit(): void {
    const savedTasks = localStorage.getItem('tasks'); // Get tasks from storage
    this.tasks = savedTasks ? JSON.parse(savedTasks) : []; // Restore if available
  }

  // Easy access to form fields (used in template)
  get f() {
    return this.taskForm.controls;
  }

  // Handles form submit: adds new task
  addTask(): void {
    this.submitted = true;

    if (this.taskForm.invalid) return; // Stop if form is invalid

    // Create task object
    const newTask: Task = {
      title: this.taskForm.value.title,
      description: this.taskForm.value.description,
      dueDate: this.taskForm.value.dueDate,
      completed: false
    };

    this.tasks.push(newTask);     // Add to task array
    this.saveTasks();             // Persist tasks
    this.taskForm.reset();        // Clear form
    this.submitted = false;       // Reset submitted state
  }

  // Delete selected task
  deleteTask(taskToDelete: Task): void {
    this.tasks = this.tasks.filter(task => task !== taskToDelete); // Remove task
    this.saveTasks(); // Save updated list
  }

  // Toggle completion status
  toggleComplete(task: Task): void {
    task.completed = !task.completed; // Flip the completed flag
    this.saveTasks(); // Save updated list
  }

  // Logout the user and redirect to login
  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  // Save task list to localStorage
  saveTasks(): void {
    localStorage.setItem('tasks', JSON.stringify(this.tasks));
  }

  //  Set filter type (all, pending, completed)
  setFilter(type: 'all' | 'pending' | 'completed') {
    this.filterType = type;
  }

  //  Apply search & filter logic and sort by due date
  filteredTasks(): Task[] {
    let filtered = this.tasks;

    //  Filter by task completion
    if (this.filterType === 'pending') {
      filtered = filtered.filter(t => !t.completed);
    } else if (this.filterType === 'completed') {
      filtered = filtered.filter(t => t.completed);
    }

    //  Filter by search query (case-insensitive)
    if (this.searchText.trim()) {
      filtered = filtered.filter(task =>
        task.title.toLowerCase().includes(this.searchText.toLowerCase())
      );
    }

    //  Sort tasks by due date (if any)
    return filtered.sort((a, b) => {
      const dateA = a.dueDate ? new Date(a.dueDate).getTime() : 0;
      const dateB = b.dueDate ? new Date(b.dueDate).getTime() : 0;
      return dateA - dateB;
    });
  }

  // ✅ Get only pending tasks
  filteredPendingTasks(): Task[] {
    return this.filteredTasks().filter(task => !task.completed);
  }

  // ✅ Get only completed tasks
  filteredCompletedTasks(): Task[] {
    return this.filteredTasks().filter(task => task.completed);
  }
}

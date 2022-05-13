import { Component, OnInit } from '@angular/core';
import { TaskService } from './task.service';
import { Task } from './task';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public tasks: Task[];

  constructor(private taskService: TaskService) {
    this.tasks = [];
  }

  ngOnInit(): void {
    this.getTasks();
  }

  public getTasks(): any {
    this.taskService.getTasks().subscribe({
      next: (data: Task[]) => { this.tasks = data; },
      error: (error: HttpErrorResponse) => { alert(error.message); }
    });
  }
}

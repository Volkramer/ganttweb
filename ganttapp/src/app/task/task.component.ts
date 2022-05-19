import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Task } from './task';
import { TaskService } from './task.service';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  public tasks: Task[];

  constructor(private taskService: TaskService) {
    this.tasks = [];
  }

  ngOnInit(): void {

  }

  public getTasks(): any {
    this.taskService.getTasks().subscribe({
      next: (data: Task[]) => { this.tasks = data; },
      error: (error: HttpErrorResponse) => { alert(error.message); }
    });
  }

}

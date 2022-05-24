import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Task } from '../Object/task';
import { TaskService } from '../services/task.service';
import { NgForm } from '@angular/forms';
import { DiagramService } from '../services/diagram.service';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  constructor(private taskService: TaskService, private diagramService: DiagramService) { }

  ngOnInit(): void {

  }

  public onOpenModal(task: Task | null, mode: string): void {
    const container = document.getElementById('main-container')
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-bs-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-bs-target', '#addTaskModal');
    }
    if (mode === 'edit') {
      button.setAttribute('data-bs-target', '#updateTaskModal');

    }
    if (mode === 'delete') {
      button.setAttribute('data-bs-target', '#deleteTaskModal');
    }
    container?.appendChild(button);
    button.click();
  }

  public onAddTask(addForm: NgForm): void {
    document.getElementById('addTaskForm-closeButton')?.click();
    this.taskService.addTask(addForm.value).subscribe({
      error: (error: HttpErrorResponse) => { alert(error.message) }
    });
  }

  public getTask(): void {
    this.taskService.getTasks().subscribe({
      next: (data: Task[]) => { this.diagramService.populateDiagram(data) },
      error: (error: HttpErrorResponse) => { alert(error.message) }
    });
  }
}

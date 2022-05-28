import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Task } from '../object/task';
import { TaskService } from '../services/task.service';
import { NgForm } from '@angular/forms';
import { DiagramService } from '../services/diagram.service';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  public task = <Task>{};

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
      if (task == undefined) { alert("Veuillez séléctionner une tâche à modifier"); return; }
      this.task = task;
      button.setAttribute('data-bs-target', '#editTaskModal');
    }
    if (mode === 'delete') {
      button.setAttribute('data-bs-target', '#deleteTaskModal');
    }
    container?.appendChild(button);
    button.click();
  }

  public onEditButton(): void {
    const node = this.diagramService.diagram.selection.first();
    if (node == undefined) { alert("Veuillez séléctionner une tâche à modifier"); return; }
    this.taskService.getTaskById(node?.data.key).subscribe({
      next: (data: Task) => {
        this.onOpenModal(data, "edit");
      },
      error: (error: HttpErrorResponse) => (alert(error.message))
    });
  }

  public onAddTask(addForm: NgForm): void {
    document.getElementById('addTaskForm-closeButton')?.click();
    this.taskService.addTask(addForm.value).subscribe({
      error: (error: HttpErrorResponse) => { alert(error.message) }
    });
  }

  public onEditTask(editForm: NgForm): void {
    document.getElementById('editTaskForm-closeButton')?.click();
    const task: Task = Object.assign(this.task, editForm);
    this.taskService.updateTask(task).subscribe({
      error: (error: HttpErrorResponse) => { alert(error.message) }
    })
  }

  public getTask(id: number): void {
    this.taskService.getTaskById(id).subscribe({
      next: (data: Task) => { }
    })
  }

  public getTasks(): void {
    this.taskService.getTasks().subscribe({
      next: (data: Task[]) => { this.diagramService.populateDiagram(data) },
      error: (error: HttpErrorResponse) => { alert(error.message) }
    });
  }
}

import { HttpErrorResponse } from '@angular/common/http';
import { Component, HostListener, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Link } from '../object/link';
import { Task } from '../object/task';
import { DiagramService } from '../services/diagram.service';
import { LinkService } from '../services/link.service';
import { TaskService } from '../services/task.service';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  public task = <Task>{};
  public link = <Link>{};

  constructor(private taskService: TaskService, private linkService: LinkService, private diagramService: DiagramService) { }

  ngOnInit(): void {
    this.diagramService.initDiagram();
    this.diagramService.addLegendDiagram();
    this.diagramService.diagram.addDiagramListener("SelectionDeleting", (event) => {
      event.diagram.selection.each((part) => {
        const key = part.key
        if (typeof key === "number") {
          if (this.diagramService.isLink(part)) {
            this.linkService.deleteLink(key).subscribe({
              next: () => { this.getTasks() },
              error: (error: HttpErrorResponse) => { alert(error.message) }
            });
          }
          if (this.diagramService.isNode(part)) {
            const node = this.diagramService.diagram.findNodeForKey(part.key);
            node?.findLinksConnected().each(link => {
              if (typeof link.key === "number") {
                this.linkService.deleteLink(link.key).subscribe({
                  next: () => { this.getTasks() },
                  error: (error: HttpErrorResponse) => { alert(error.message) }
                })
              }
            });
            this.taskService.deleteTask(key).subscribe({
              next: () => { this.getTasks() },
              error: (error: HttpErrorResponse) => { alert(error.message) }
            })
          }
        }
      });
    });
    this.diagramService.diagram.addDiagramListener("LinkDrawn", (event) => {
      const linkDraw = event.diagram.selection.first();
      this.link.fromTask = linkDraw?.data.from;
      this.link.toTask = linkDraw?.data.to;
      this.linkService.addLink(this.link).subscribe({
        next: () => { this.getTasks() },
        error: (error: HttpErrorResponse) => { alert(error.message) }
      })
    });
  }

  ngAfterViewInit(): void {
    this.getTasks();
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
      if (task == undefined) { alert("Veuillez séléctionner une tâche à modifier"); return; }
      this.task = task;
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

  public onDeleteButton(): void {
    const node = this.diagramService.diagram.selection.first();
    if (node == undefined) { alert("Veuillez séléctionner une tâche à supprimer"); return; }
    this.taskService.getTaskById(node?.data.key).subscribe({
      next: (data: Task) => {
        this.onOpenModal(data, "delete");
      },
      error: (error: HttpErrorResponse) => (alert(error.message))
    });
  }

  public onAddTask(addForm: NgForm): void {
    document.getElementById('addTaskForm-closeButton')?.click();
    this.taskService.addTask(addForm.value).subscribe({
      next: () => { this.getTasks() },
      error: (error: HttpErrorResponse) => { alert(error.message) }
    });
  }

  public onEditTask(editForm: NgForm): void {
    document.getElementById('editTaskForm-closeButton')?.click();
    const task: Task = Object.assign(this.task, editForm);
    this.taskService.updateTask(task).subscribe({
      next: () => { this.getTasks() },
      error: (error: HttpErrorResponse) => { alert(error.message) }
    })
  }

  public onDeleteTask(): void {
    document.getElementById('deleteTask-closeButton')?.click();
    this.taskService.deleteTask(this.task.id).subscribe({
      next: () => { this.getTasks() },
      error: (error: HttpErrorResponse) => { alert(error.message) }
    })
  }

  public getTask(id: number): void {
    this.taskService.getTaskById(id).subscribe({
      next: (data: Task) => { this.task = data },
      error: (error: HttpErrorResponse) => { alert(error.message) }
    })
  }

  public getTasks(): void {
    forkJoin({
      dataNode: this.taskService.getTasks(),
      dataLink: this.linkService.getLinks()
    })
      .subscribe({
        next: ({ dataNode, dataLink }) => {
          this.diagramService.populateDiagram(dataNode, dataLink);
          console.log(dataNode);
        },
        error: (error: HttpErrorResponse) => { alert(error.message) }
      });

  }
}

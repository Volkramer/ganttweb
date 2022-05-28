import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import * as go from 'gojs';
import { Task } from '../object/task';
import { DiagramService } from '../services/diagram.service';
import { TaskService } from '../services/task.service';

const $ = go.GraphObject.make;

@Component({
  selector: 'app-pertt',
  templateUrl: './pertt.component.html',
  styleUrls: ['./pertt.component.css']
})
export class PerttComponent implements OnInit {

  constructor(private taskService: TaskService, private diagramService: DiagramService) {

  }

  ngOnInit() {
    this.diagramService.initDiagram();
    this.diagramService.addLegendDiagram();
  }

  ngAfterViewInit(): void {
    this.getTask();
  }

  public getTask(): void {
    this.taskService.getTasks().subscribe({
      next: (data: Task[]) => { this.diagramService.populateDiagram(data) },
      error: (error: HttpErrorResponse) => { alert(error.message) }
    });
  }


}



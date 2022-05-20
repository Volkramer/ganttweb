import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import * as go from 'gojs';
import { Task } from '../task/task';
import { TaskService } from '../services/task.service';

const $ = go.GraphObject.make;

@Component({
  selector: 'app-pertt',
  templateUrl: './pertt.component.html',
  styleUrls: ['./pertt.component.css']
})
export class PerttComponent implements OnInit {
  private blue = "#0288D1";
  private pink = "#B71C1C";
  private pinkfill = "#F8BBD0";
  private bluefill = "#B3E5FC";
  public diagram: go.Diagram = new go.Diagram;
  public state = {
    diagramNodeData: new Array(),
    diagramLinkData: new Array(),
  }
  public diagramDivClassName: string = 'perttDiagram';


  constructor(private taskService: TaskService) {
    this.state.diagramLinkData = [
      { from: 1, to: 2 },
      { from: 1, to: 3 },
      { from: 2, to: 4 },
      { from: 2, to: 5 },
      { from: 3, to: 6 },
      { from: 4, to: 6 },
      { from: 5, to: 7 },
      { from: 6, to: 8 },
      { from: 7, to: 9 },
      { from: 8, to: 9 }
    ];
  }

  ngOnInit() {
    this.initDiagram();
    this.addLegendDiagram();
  }

  ngAfterViewInit(): void {
    this.taskService.getTasks().subscribe({
      next: (data: Task[]) => { this.populateDiagram(data) },
      error: (error: HttpErrorResponse) => { alert(error.message) }
    });
  }

  public getTasks(): any {
    var tasks = new Array();
    this.taskService.getTasks().subscribe({
      next: (data: Task[]) => {
        data.forEach(element => {
          tasks.push(element);
        });
      },
      error: (error: HttpErrorResponse) => { alert(error.message) }
    });
    return tasks;
  }

  public populateDiagram(data: any[]): void {
    data.forEach(task => {
      this.state.diagramNodeData.push({ key: task.id, text: task.name, lenght: task.duration, earlyStart: task.startAsap, lateFinish: task.endLatest, critical: false });
    });
    this.diagram.model = new go.GraphLinksModel(this.state.diagramNodeData, this.state.diagramLinkData);
  }

  public initDiagram() {
    this.diagram = $(go.Diagram, this.diagramDivClassName, {
      initialAutoScale: go.Diagram.Uniform,
      layout: $(go.LayeredDigraphLayout)
    });
    this.diagram.nodeTemplate =
      $(go.Node, "Auto",
        $(go.Shape, "Rectangle",  // the border
          { fill: "white", strokeWidth: 2 },
          new go.Binding("fill", "critical", b => b ? this.pinkfill : this.bluefill),
          new go.Binding("stroke", "critical", b => b ? this.pink : this.blue)),
        $(go.Panel, "Table",
          { padding: 0.5 },
          $(go.RowColumnDefinition, { column: 1, separatorStroke: "black" }),
          $(go.RowColumnDefinition, { column: 2, separatorStroke: "black" }),
          $(go.RowColumnDefinition, { row: 1, separatorStroke: "black", background: "white", coversSeparators: true }),
          $(go.RowColumnDefinition, { row: 2, separatorStroke: "black" }),
          $(go.TextBlock, // earlyStart
            new go.Binding("text", "earlyStart"),
            { row: 0, column: 0, margin: 5, textAlign: "center" }),
          $(go.TextBlock,
            new go.Binding("text", "length"),
            { row: 0, column: 1, margin: 5, textAlign: "center" }),
          $(go.TextBlock,  // earlyFinish
            new go.Binding("text", "",
              d => (d.earlyStart + d.length).toFixed(2)),
            { row: 0, column: 2, margin: 5, textAlign: "center" }),
          $(go.TextBlock,
            new go.Binding("text", "text"),
            {
              row: 1, column: 0, columnSpan: 3, margin: 5,
              textAlign: "center", font: "bold 14px sans-serif"
            }),
          $(go.TextBlock,  // lateStart
            new go.Binding("text", "",
              d => (d.lateFinish - d.length).toFixed(2)),
            { row: 2, column: 0, margin: 5, textAlign: "center" }),
          $(go.TextBlock,  // slack
            new go.Binding("text", "",
              d => (d.lateFinish - (d.earlyStart + d.length)).toFixed(2)),
            { row: 2, column: 1, margin: 5, textAlign: "center" }),
          $(go.TextBlock, // lateFinish
            new go.Binding("text", "lateFinish"),
            { row: 2, column: 2, margin: 5, textAlign: "center" })
        )  // end Table Panel
      );
  }

  public addLegendDiagram() {
    this.diagram.add(
      $(go.Node, "Auto",
        $(go.Shape, "Rectangle",  // the border
          { fill: this.bluefill }),
        $(go.Panel, "Table",
          $(go.RowColumnDefinition, { column: 1, separatorStroke: "black" }),
          $(go.RowColumnDefinition, { column: 2, separatorStroke: "black" }),
          $(go.RowColumnDefinition, { row: 1, separatorStroke: "black", background: this.bluefill, coversSeparators: true }),
          $(go.RowColumnDefinition, { row: 2, separatorStroke: "black" }),
          $(go.TextBlock, "Early Start",
            { row: 0, column: 0, margin: 5, textAlign: "center" }),
          $(go.TextBlock, "Length",
            { row: 0, column: 1, margin: 5, textAlign: "center" }),
          $(go.TextBlock, "Early Finish",
            { row: 0, column: 2, margin: 5, textAlign: "center" }),

          $(go.TextBlock, "Activity Name",
            {
              row: 1, column: 0, columnSpan: 3, margin: 5,
              textAlign: "center", font: "bold 14px sans-serif"
            }),

          $(go.TextBlock, "Late Start",
            { row: 2, column: 0, margin: 5, textAlign: "center" }),
          $(go.TextBlock, "Slack",
            { row: 2, column: 1, margin: 5, textAlign: "center" }),
          $(go.TextBlock, "Late Finish",
            { row: 2, column: 2, margin: 5, textAlign: "center" })
        )  // end Table Panel
      ));
  }
}



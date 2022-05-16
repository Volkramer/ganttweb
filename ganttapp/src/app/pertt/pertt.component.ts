import { Component, OnInit } from '@angular/core';
import * as go from 'gojs';
import { DataSyncService, DiagramComponent, PaletteComponent } from 'gojs-angular';

@Component({
  selector: 'app-pertt',
  templateUrl: './pertt.component.html',
  styleUrls: ['./pertt.component.css']
})
export class PerttComponent implements OnInit {
  private $ = go.GraphObject.make;
  private perttDiagram = {};
  private blue = "#0288D1";
  private pink = "#B71C1C";
  private pinkfill = "#F8BBD0";
  private bluefill = "#B3E5FC";
  public state = {};
  public diagramDivClassName: string = 'perttDiagram';
  public paletteDivClassName = "perttPalette";

  constructor() { }

  ngOnInit(): void {
  }

  public initDiagram(): go.Diagram {
    const diagram = this.$(go.Diagram, this.diagramDivClassName, {
      initialAutoScale: go.Diagram.Uniform,
      layout: this.$(go.LayeredDigraphLayout)
    });
    diagram.nodeTemplate =
      this.$(go.Node, "Auto",
        this.$(go.Shape, "Rectangle",  // the border
          { fill: "white", strokeWidth: 2 },
          new go.Binding("fill", "critical", b => b ? this.pinkfill : this.bluefill),
          new go.Binding("stroke", "critical", b => b ? this.pink : this.blue)),
        this.$(go.Panel, "Table",
          { padding: 0.5 },
          this.$(go.RowColumnDefinition, { column: 1, separatorStroke: "black" }),
          this.$(go.RowColumnDefinition, { column: 2, separatorStroke: "black" }),
          this.$(go.RowColumnDefinition, { row: 1, separatorStroke: "black", background: "white", coversSeparators: true }),
          this.$(go.RowColumnDefinition, { row: 2, separatorStroke: "black" }),
          this.$(go.TextBlock, // earlyStart
            new go.Binding("text", "earlyStart"),
            { row: 0, column: 0, margin: 5, textAlign: "center" }),
          this.$(go.TextBlock,
            new go.Binding("text", "length"),
            { row: 0, column: 1, margin: 5, textAlign: "center" }),
          this.$(go.TextBlock,  // earlyFinish
            new go.Binding("text", "",
              d => (d.earlyStart + d.length).toFixed(2)),
            { row: 0, column: 2, margin: 5, textAlign: "center" }),

          this.$(go.TextBlock,
            new go.Binding("text", "text"),
            {
              row: 1, column: 0, columnSpan: 3, margin: 5,
              textAlign: "center", font: "bold 14px sans-serif"
            }),

          this.$(go.TextBlock,  // lateStart
            new go.Binding("text", "",
              d => (d.lateFinish - d.length).toFixed(2)),
            { row: 2, column: 0, margin: 5, textAlign: "center" }),
          this.$(go.TextBlock,  // slack
            new go.Binding("text", "",
              d => (d.lateFinish - (d.earlyStart + d.length)).toFixed(2)),
            { row: 2, column: 1, margin: 5, textAlign: "center" }),
          this.$(go.TextBlock, // lateFinish
            new go.Binding("text", "lateFinish"),
            { row: 2, column: 2, margin: 5, textAlign: "center" })
        )  // end Table Panel
      );
    return diagram;
  }
}



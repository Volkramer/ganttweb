import { Injectable } from '@angular/core';
import * as go from 'gojs';

const $ = go.GraphObject.make;

@Injectable({
  providedIn: 'root'
})
export class PertDiagramService {
  private blue = "#0288D1";
  private pink = "#B71C1C";
  private pinkfill = "#F8BBD0";
  private bluefill = "#B3E5FC";
  public diagram: go.Diagram = new go.Diagram();
  public state = {
    diagramNodeData: new Array(),
    diagramLinkData: new Array(),
  }
  public diagramDivClassName: string = 'perttDiagram';
  public nodesData = new Array();
  public linksData = new Array();

  constructor() { }

  public isLink(part: any): boolean {
    return part instanceof go.Link;
  }

  public isNode(part: any): boolean {
    return part instanceof go.Node;
  }

  public populateDiagram(dataNodes: any[], dataLinks: any[]): void {
    this.state.diagramNodeData = [];
    this.state.diagramLinkData = [];
    dataNodes.forEach(node => {
      this.state.diagramNodeData.push({ key: node.id, nbr: node.nbr, name: node.name, length: node.duration, earlyStart: node.startAsap, lateStart: node.startLatest, earlyFinish: node.endAsap, lateFinish: node.endLatest, slack: node.marginTotal, critical: false });
    });
    dataLinks.forEach(link => {
      this.state.diagramLinkData.push({ key: link.id, from: link.fromTask, to: link.toTask });
    })
    this.diagram.model = new go.GraphLinksModel(this.state.diagramNodeData, this.state.diagramLinkData, { linkKeyProperty: "key" });
  }

  public initDiagram() {
    this.diagram = $(go.Diagram, this.diagramDivClassName, {
      initialAutoScale: go.Diagram.Uniform,
      layout: $(go.LayeredDigraphLayout)
    });
    this.diagram.nodeTemplate =
      $(go.Node, "Auto",
        $(go.Shape, "RoundedRectangle",  // the border
          {
            fill: "white", strokeWidth: 2, portId: "", cursor: "pointer",
            fromLinkable: true, toLinkable: true,
            fromLinkableDuplicates: false, toLinkableDuplicates: false
          },
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
            new go.Binding("text", "earlyFinish"),
            { row: 0, column: 2, margin: 5, textAlign: "center" }),
          $(go.TextBlock,
            new go.Binding("text", "name"),
            {
              row: 1, column: 0, columnSpan: 3, margin: 5,
              textAlign: "center", font: "bold 14px sans-serif"
            }),
          $(go.TextBlock,  // lateStart
            new go.Binding("text", "lateStart"),
            { row: 2, column: 0, margin: 5, textAlign: "center" }),
          $(go.TextBlock,  // slack
            new go.Binding("text", "slack"),
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

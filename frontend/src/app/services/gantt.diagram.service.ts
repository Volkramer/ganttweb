import { Injectable } from '@angular/core';
import * as go from 'gojs';

const $ = go.GraphObject.make;

@Injectable({
  providedIn: 'root'
})
export class GanttDiagramService extends go.Layout {

}

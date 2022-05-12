import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiServerUrl = '';

  constructor(private http: HttpClient) { }

  public getTasks(): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/task/all`);
  }
}

import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Link } from "../Object/link";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LinkService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getLinks(): Observable<Link[]> {
    return this.http.get<Link[]>(`${this.apiServerUrl}/link/all`);
  }

  public getLinkById(linkId: number): Observable<Link> {
    return this.http.get<Link>(`${this.apiServerUrl}/link/find/${linkId}`);
  }

  public addLink(link: Link): Observable<Link> {
    return this.http.post<Link>(`${this.apiServerUrl}/link/add`, link);
  }

  public updateLink(link: Link): Observable<Link> {
    return this.http.put<Link>(`${this.apiServerUrl}/link/update`, link);
  }

  public deleteLink(linkId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/link/delete/${linkId}`);
  }
}

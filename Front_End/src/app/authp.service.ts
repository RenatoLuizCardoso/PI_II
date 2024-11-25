import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthpService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/teacher/auth';

  constructor(private http: HttpClient) {}

  login(payload: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(this.apiUrl, payload, { headers });
  }
}

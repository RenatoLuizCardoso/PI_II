// autha.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthaService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/auth/admin';

  constructor(private http: HttpClient) {}

  login(payload: string): Observable<string> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(this.apiUrl, payload, { headers, responseType: 'text' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PerfilService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/teacher/';

  constructor(private http: HttpClient) {}

  getProfile(): Observable<any> {
    const token = localStorage.getItem('teacherToken');

    if (!token) {
      throw new Error('Token não encontrado');
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`  // Adicionando o token no cabeçalho para autenticação
    });

    return this.http.get<any>(this.apiUrl, { headers });
  }
}

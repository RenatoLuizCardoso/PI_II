import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ChoraService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/time/';

  constructor(private http: HttpClient) {}

  // Função para obter os headers com o token de autenticação
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('adminToken'); // Obtém o token do localStorage
    if (!token) {
      throw new Error('Token de autenticação não encontrado.');
    }

    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  // Obtém todos os horários com autenticação
  getTimes(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { headers: this.getAuthHeaders() });
  }

  // Exclui um horário pelo ID com autenticação
  deleteTime(timeId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}${timeId}`, {
      headers: this.getAuthHeaders(),
    });
  }

  // Adiciona um novo horário com autenticação
  addTime(newTime: { startTime: string; endTime: string }): Observable<any> {
    return this.http.post<any>(this.apiUrl, newTime, {
      headers: this.getAuthHeaders(),
    });
  }
}

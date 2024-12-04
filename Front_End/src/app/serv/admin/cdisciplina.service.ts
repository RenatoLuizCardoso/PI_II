import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CdisciplinaService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/subject/';

  constructor(private http: HttpClient) {}

  // Função para obter os headers com o token de autenticação
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('adminToken');
    if (!token) {
      throw new Error('Token de autenticação não encontrado.');
    }

    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // Método para registrar uma nova disciplina com autenticação
  registerDisciplines(disciplineData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post<any>(this.apiUrl, disciplineData, { headers });
  }

  // Método para obter todas as disciplinas com autenticação
  getDisciplines(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.apiUrl, { headers });
  }

  // Método para obter uma disciplina por ID com autenticação
  getDisciplineById(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get<any>(`${this.apiUrl}${id}`, { headers });
  }

  // Método para atualizar uma disciplina com autenticação
  updateDiscipline(discipline: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put<any>(`${this.apiUrl}${discipline.subjectId}`, discipline, { headers });
  }

  // Método para excluir uma disciplina com autenticação
  deleteDiscipline(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.delete<any>(`${this.apiUrl}${id}`, { headers });
  }
}

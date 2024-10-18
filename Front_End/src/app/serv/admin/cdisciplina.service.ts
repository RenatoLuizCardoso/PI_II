// cdisciplina.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CdisciplinaService {
  private apiUrl = 'http://localhost:3000/disciplinas';
  private professoresUrl = 'http://localhost:3000/professores'; // Endpoint para buscar professores

  constructor(private http: HttpClient) { }

  // Método para registrar uma nova disciplina
  registerDisciplina(disciplinaData: any): Observable<any> {
    return this.http.post(this.apiUrl, disciplinaData);
  }

  // Método para buscar professores
  getProfessores(): Observable<any> {
    return this.http.get<any[]>(this.professoresUrl);
  }
  getDisciplinas(): Observable<any> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getDisciplinaById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  updateDisciplina(disciplina: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${disciplina.id}`, disciplina);
  }

  // cdisciplina.service.ts
  deleteDisciplina(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

}

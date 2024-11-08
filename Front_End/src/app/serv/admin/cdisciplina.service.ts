// cdisciplina.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CdisciplinaService {
  private apiUrl = 'http://localhost:3000/disciplines';


  constructor(private http: HttpClient) { }

  // Método para registrar uma nova disciplina
  registerDisciplines(disciplineData: any): Observable<any> {
    return this.http.post(this.apiUrl, disciplineData);
  }


  getDisciplines(): Observable<any> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getDisciplineById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  updateDiscipline(discipline: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${discipline.id}`, discipline);
  }

  // cdisciplina.service.ts
  deleteDiscipline(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

}

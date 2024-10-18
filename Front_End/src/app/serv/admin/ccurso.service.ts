

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CcursoService {
  private apiUrl = 'http://localhost:3000/cursos';

  constructor(private http: HttpClient) { }

  // Método para registrar um novo professor
  registerCurso(cursoData: any): Observable<any> {
    return this.http.post(this.apiUrl, cursoData);
  }

    // Método para obter a lista de professores
    getCursos(): Observable<any[]> {
      return this.http.get<any[]>(this.apiUrl);
    }

    getCursoById(id: string): Observable<any> {
      return this.http.get<any>(`${this.apiUrl}/${id}`);
    }
  
    updateCurso(curso: any): Observable<any> {
      return this.http.put<any>(`${this.apiUrl}/${curso.id}`, curso);
    }
  
  
    deleteCurso(id: number): Observable<any> {
      return this.http.delete(`${this.apiUrl}/${id}`);
    }
  }



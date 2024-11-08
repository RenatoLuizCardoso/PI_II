import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfessoresService {
   private apiUrl = 'http://localhost:3000/professores';
  private cursosUrl = 'http://localhost:3000/cursos';
  // private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/teachers';
  // private cursosUrl = 'https://projeto-integrador-1v4i.onrender.com/courses';

  // https://projeto-integrador-1v4i.onrender.com

  constructor(private http: HttpClient) { }

  // Método para registrar um novo professor
  registerProfessor(professorData: any): Observable<any> {
    return this.http.post(this.apiUrl, professorData);
  }

  // Método para obter a lista de professores
  getProfessores(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
  getCursos(): Observable<any> {
    return this.http.get<any[]>(this.cursosUrl);
  }

  getProfessorById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  updateProfessor(professor: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${professor.id}`, professor);
  }


  deleteProfessor(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }


  loginProfessor(loginData: { emailI: string, senha: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}`, loginData); // Certifique-se de que o endpoint de login seja correto
  }
}

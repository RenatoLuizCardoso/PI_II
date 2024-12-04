import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CcursoService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/course/';  // URL principal para cursos
  private disciplinesUrl = 'https://projeto-integrador-1v4i.onrender.com/subject/';  // URL para buscar as disciplinas

  constructor(private http: HttpClient) { }

  // Função para obter os headers com o token
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

  // Método para registrar um novo curso com o Bearer Token
  registerCurso(cursoData: any): Observable<any> {
    const headers = this.getAuthHeaders(); // Obtém os headers com o token
    return this.http.post<any>(this.apiUrl, cursoData, { headers });
  }

  // Método para obter a lista de cursos com autenticação
  getCursos(): Observable<any[]> {
    const headers = this.getAuthHeaders(); // Obtém os headers com o token
    return this.http.get<any[]>(this.apiUrl, { headers });
  }

  // Método para obter a lista de disciplinas com autenticação
  getDisciplines(): Observable<any[]> {
    const headers = this.getAuthHeaders(); // Obtém os headers com o token
    return this.http.get<any[]>(this.disciplinesUrl, { headers });  // Alterado para a URL correta das disciplinas
  }

  // Método para obter um curso específico por ID com autenticação
  getCursoById(id: string): Observable<any> {
    const headers = this.getAuthHeaders(); // Obtém os headers com o token
    return this.http.get<any>(`${this.apiUrl}${id}`, { headers });
  }

  // Método para atualizar um curso com autenticação
  updateCurso(curso: any): Observable<any> {
    const headers = this.getAuthHeaders(); // Obtém os headers com o token
    return this.http.put<any>(`${this.apiUrl}${curso.courseId}`, curso, { headers });
  }

  // Método para excluir um curso com autenticação
  deleteCurso(id: number): Observable<any> {
    const headers = this.getAuthHeaders(); // Obtém os headers com o token
    return this.http.delete<any>(`${this.apiUrl}${id}`, { headers });
  }
}

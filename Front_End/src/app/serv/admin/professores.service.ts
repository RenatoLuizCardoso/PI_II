import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfessoresService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/teacher/';  // Nova URL da API
  private cursosUrl = 'https://projeto-integrador-1v4i.onrender.com/courses'; // Caso precise acessar cursos também

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

  // Método para registrar um novo professor com o Bearer Token
  registerProfessor(professorData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post<any>(this.apiUrl, professorData, { headers });
  }

  // Método para obter a lista de todos os professores
  getProfessores(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.apiUrl, { headers });
  }

  // Método para obter um professor específico pelo ID
  getProfessorById(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get<any>(`${this.apiUrl}${id}`, { headers });
  }

  // Método para atualizar as informações de um professor
  updateProfessor(professor: any): Observable<any> {
    const headers = this.getAuthHeaders();
    const professorId = professor.id || professor.teacherId; // Ajuste para o nome correto do ID
    return this.http.put<any>(`${this.apiUrl}/${professorId}`, professor, { headers });
  }

  deleteProfessor(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.delete<any>(`${this.apiUrl}${id}`, { headers });
  }


  // Método para obter a lista de cursos (caso necessário)
  getCursos(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.cursosUrl, { headers });
  }

  // Método para obter um curso específico pelo ID (caso necessário)
  getCursoById(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get<any>(`${this.cursosUrl}/${id}`, { headers });
  }
}

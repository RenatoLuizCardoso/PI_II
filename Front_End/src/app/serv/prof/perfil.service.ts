import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PerfilService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/teacher/';
  private subjectsUrl = 'https://projeto-integrador-1v4i.onrender.com/subject/';  // Endpoint de subjects

  constructor(private http: HttpClient) {}

  // Método para obter os dados do perfil
  getProfile(): Observable<any> {
    const token = localStorage.getItem('teacherToken');

    if (!token) {
      throw new Error('Token não encontrado');
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.get<any>(this.apiUrl, { headers });
  }

  // Método para obter todos os subjects
  getSubjects(): Observable<any[]> {
    const token = localStorage.getItem('teacherToken');  // Obtém o token do localStorage
  
    if (!token) {
      throw new Error('Token não encontrado');
    }
  
    // Cria os cabeçalhos com o token de autorização
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  
    // Faz a requisição GET com o cabeçalho de autorização
    return this.http.get<any[]>(this.subjectsUrl, { headers });
  }
  
  
  // Método otimizado para carregar os subjects como um mapa de {id: name}
  getSubjectsMap(): Observable<{ [key: string]: string }> {
    return new Observable((observer) => {
      this.getSubjects().subscribe({
        next: (subjects) => {
          // Criar um mapeamento de subjectId para subjectName
          const subjectMap = subjects.reduce((acc, subject) => {
            acc[subject.subjectId] = subject.subjectName;  // mapeia {subjectId: subjectName}
            return acc;
          }, {});
          
          observer.next(subjectMap);  // Emite o mapeamento
          observer.complete();
        },
        error: (err) => {
          console.error('Erro ao carregar os subjects', err);
          observer.error(err);  // Passa o erro para o observador
        }
      });
    });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CsalasService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/rooms/';

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

  // Método para registrar uma nova sala com o Bearer Token
  registerSala(salaData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post<any>(this.apiUrl, salaData, { headers });
  }

  // Método para recuperar as salas com autenticação
  getSalas(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.apiUrl, { headers });
  }

  // Método para obter uma sala por ID com autenticação
  getSalaById(id: string): Observable<any> {
    const headers = this.getAuthHeaders();
    // Corrigir a URL para evitar duplicação de barras
    return this.http.get<any>(`${this.apiUrl}${id}`, { headers });
  }

  // Método para atualizar uma sala com autenticação
  updateSala(sala: any): Observable<any> {
    const headers = this.getAuthHeaders();

    // Certifique-se de que o ID é passado corretamente na URL
    const salaId = sala.id || sala.roomId;  // Ajuste para o nome correto do ID
    return this.http.put<any>(`${this.apiUrl}${salaId}`, sala, { headers });
  }

  // Método para excluir uma sala com autenticação
  deleteSala(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.delete<any>(`${this.apiUrl}${id}`, { headers });
  }
}

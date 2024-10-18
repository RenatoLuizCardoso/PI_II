import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CsalasService {
  private apiUrl = 'http://localhost:3000/salas';

  constructor(private http: HttpClient) { }

  // Método para registrar um novo professor
  registerSala(salaData: any): Observable<any> {
    return this.http.post(this.apiUrl, salaData);
  }

  getSalas(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getSalaById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  updateSala(sala: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${sala.id}`, sala);
  }

  deleteSala(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GhorarioService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/reservation/';
  private teachersUrl = 'https://projeto-integrador-1v4i.onrender.com/teacher/';
  private subjectsUrl = 'https://projeto-integrador-1v4i.onrender.com/subject/';
  private timesUrl = 'https://projeto-integrador-1v4i.onrender.com/time/';
  private roomsUrl = 'https://projeto-integrador-1v4i.onrender.com/rooms/';
  private coursesUrl = 'https://projeto-integrador-1v4i.onrender.com/course/';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token de autenticação não encontrado.');
    }
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // CRUD para reservas
  createReservation(reservationData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.apiUrl, reservationData, { headers });
  }

  getReservations(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.apiUrl, { headers });
  }

  updateReservation(reservation: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put<any>(`${this.apiUrl}${reservation.reservationId}`, reservation, { headers });
  }

  deleteReservation(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.delete(`${this.apiUrl}${id}`, { headers });
  }

  
  // Métodos adicionais para obter listas dinâmicas
  getTeachers(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.teachersUrl, { headers });
  }

  getSubjects(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.subjectsUrl, { headers });
  }

  getTimes(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.timesUrl, { headers });
  }

  getRooms(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.roomsUrl, { headers });
  }

  getCourses(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.coursesUrl, { headers });
  }
}

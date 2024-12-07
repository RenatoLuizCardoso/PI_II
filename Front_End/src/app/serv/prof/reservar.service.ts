import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReservarService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/reservation/'; // Endpoint para reservas
  private scheduleUrl = 'https://projeto-integrador-1v4i.onrender.com/schedule/'; // Novo endpoint para agendamentos
  private teachersUrl = 'https://projeto-integrador-1v4i.onrender.com/teacher/';
  private subjectsUrl = 'https://projeto-integrador-1v4i.onrender.com/subject/';
  private timesUrl = 'https://projeto-integrador-1v4i.onrender.com/time/';
  private roomsUrl = 'https://projeto-integrador-1v4i.onrender.com/rooms/';
  private coursesUrl = 'https://projeto-integrador-1v4i.onrender.com/course/';

  constructor(private http: HttpClient) {}

  // Método para obter os headers de autenticação (Token)
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('teacherToken');
    if (!token) {
      throw new Error('Token de autenticação não encontrado.');
    }
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    });
  }

  // CRUD para reservas

  createReservation(reservationData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.apiUrl, reservationData, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao criar reserva:', error);
        return throwError(() => new Error('Erro ao criar reserva'));
      })
    );
  }

  getReservations(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.apiUrl, { headers }).pipe(
      tap((response) => console.log('Reservas recebidas:', response)),
      catchError((error) => {
        console.error('Erro ao obter reservas:', error);
        return throwError(() => new Error('Falha ao obter reservas'));
      })
    );
  }

  updateReservation(reservation: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put<any>(`${this.apiUrl}${reservation.reservationId}`, reservation, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao atualizar reserva:', error);
        return throwError(() => new Error('Erro ao atualizar reserva'));
      })
    );
  }

  deleteReservation(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.delete(`${this.apiUrl}${id}`, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao excluir reserva:', error);
        return throwError(() => new Error('Erro ao excluir reserva'));
      })
    );
  }

  // Método para criar um agendamento
  createSchedule(scheduleData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.scheduleUrl, scheduleData, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao criar agendamento:', error);
        return throwError(() => new Error('Erro ao criar agendamento'));
      })
    );
  }

  // Métodos adicionais para obter listas dinâmicas

  getTeachers(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.teachersUrl, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao obter professores:', error);
        return throwError(() => new Error('Erro ao obter professores'));
      })
    );
  }

  getSubjects(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.subjectsUrl, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao obter matérias:', error);
        return throwError(() => new Error('Erro ao obter matérias'));
      })
    );
  }

  getTimes(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.timesUrl, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao obter horários:', error);
        return throwError(() => new Error('Erro ao obter horários'));
      })
    );
  }

  getRooms(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.roomsUrl, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao obter salas:', error);
        return throwError(() => new Error('Erro ao obter salas'));
      })
    );
  }

  getCourses(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.coursesUrl, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao obter cursos:', error);
        return throwError(() => new Error('Erro ao obter cursos'));
      })
    );
  }

  
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GradeFixaService {
  private apiUrl = 'https://projeto-integrador-1v4i.onrender.com/reservation/';
  private teachersUrl = 'https://projeto-integrador-1v4i.onrender.com/teacher/';
  private subjectsUrl = 'https://projeto-integrador-1v4i.onrender.com/subject/';
  private timesUrl = 'https://projeto-integrador-1v4i.onrender.com/time/';
  private roomsUrl = 'https://projeto-integrador-1v4i.onrender.com/rooms/';
  private coursesUrl = 'https://projeto-integrador-1v4i.onrender.com/course/';

  constructor(private http: HttpClient) {}

  // Método para obter os cabeçalhos de autenticação
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('adminToken');
    if (!token) {
      throw new Error('Token de autenticação não encontrado.');
    }
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    });
  }

  // CRUD para agendamentos
  createSchedule(scheduleData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.apiUrl, scheduleData, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao criar agendamento:', error);
        return throwError(() => new Error('Erro ao criar agendamento'));
      })
    );
  }

  getSchedules(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(this.apiUrl, { headers }).pipe(
      tap((response) => console.log('Agendamentos recebidos:', response)),
      catchError((error) => {
        console.error('Erro ao obter agendamentos:', error);
        return throwError(() => new Error('Falha ao obter agendamentos'));
      })
    );
  }

  updateSchedule(schedule: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put<any>(`${this.apiUrl}${schedule.scheduleId}`, schedule, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao atualizar agendamento:', error);
        return throwError(() => new Error('Erro ao atualizar agendamento'));
      })
    );
  }

  deleteSchedule(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.delete(`${this.apiUrl}${id}`, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao excluir agendamento:', error);
        return throwError(() => new Error('Erro ao excluir agendamento'));
      })
    );
  }

  // Métodos para cadastrar dados auxiliares
  createTeacher(teacherData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.teachersUrl, teacherData, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao criar professor:', error);
        return throwError(() => new Error('Erro ao criar professor'));
      })
    );
  }

  createSubject(subjectData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.subjectsUrl, subjectData, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao criar matéria:', error);
        return throwError(() => new Error('Erro ao criar matéria'));
      })
    );
  }

  createRoom(roomData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.roomsUrl, roomData, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao criar sala:', error);
        return throwError(() => new Error('Erro ao criar sala'));
      })
    );
  }

  createCourse(courseData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.coursesUrl, courseData, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao criar curso:', error);
        return throwError(() => new Error('Erro ao criar curso'));
      })
    );
  }

  // Métodos para obter dados auxiliares
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

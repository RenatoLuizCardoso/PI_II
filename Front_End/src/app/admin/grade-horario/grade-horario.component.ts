import { Component, OnInit } from '@angular/core';
import { GhorarioService } from '../../serv/admin/ghorario.service';

// Definição da interface para os dados do formulário
interface ReservationForm {
  teacher: number | null;
  subject: number | null;
  time: number | null;
  date: string;
  room: number | null;
  course: number | null;
}

interface Reservation {
  reservationId: number;
  teacher: number;
  subject: number;
  time: number;
  date: string;
  room: number;
  course: number;
}

@Component({
  selector: 'app-grade-horario',
  templateUrl: './grade-horario.component.html',
  styleUrls: ['./grade-horario.component.css']
})
export class GradeHorarioComponent implements OnInit {
  reservations: Reservation[] = [];
  teachers: any[] = [];
  subjects: any[] = [];
  times: any[] = [];
  rooms: any[] = [];
  courses: any[] = [];

  form: ReservationForm = { teacher: null, subject: null, time: null, date: '', room: null, course: null };
  isEditing: boolean = false;
  loading: boolean = false;

  constructor(private ghorarioService: GhorarioService) {}

  ngOnInit(): void {
    this.loadReservations();
    this.loadAuxiliaryData();
  }

  // Método para carregar as reservas
  loadReservations(): void {
    this.loading = true;
    this.ghorarioService.getReservations().subscribe({
      next: (data) => {
        this.reservations = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar reservas:', err);
        this.loading = false;
      }
    });
  }

  logSelectedValue(field: string, value: any): void {
    console.log(`${field} selecionado:`, value);
  }

  // Método para carregar os dados auxiliares (listas dinâmicas)
  loadAuxiliaryData(): void {
    this.ghorarioService.getTeachers().subscribe(data => {
      console.log('Teachers:', data);
      this.teachers = data;
    });
    this.ghorarioService.getSubjects().subscribe(data => {
      console.log('Subjects:', data);
      this.subjects = data;
    });
    this.ghorarioService.getTimes().subscribe(data => {
      console.log('Times:', data);
      this.times = data;
    });
    this.ghorarioService.getRooms().subscribe(data => {
      console.log('Rooms:', data);
      this.rooms = data;
    });
    this.ghorarioService.getCourses().subscribe(data => {
      console.log('Courses:', data);
      this.courses = data;
    });
  }

  // Método para criar uma nova reserva
  createReservation(): void {
    this.loading = true;
    const formattedData = this.formatReservationData(this.form);
    console.log('Dados enviados para criação de reserva:', formattedData);
    
    this.ghorarioService.createReservation(formattedData).subscribe({
      next: () => {
        this.loadReservations();
        this.resetForm();
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao criar reserva:', err);
        this.loading = false;
      }
    });
  }

  // Método para editar uma reserva
  editReservation(reservation: Reservation): void {
    this.isEditing = true;
    this.form = { 
      teacher: reservation.teacher,
      subject: reservation.subject,
      time: reservation.time,
      date: reservation.date,
      room: reservation.room,
      course: reservation.course
    };
  }

  // Método para atualizar uma reserva
  updateReservation(): void {
    this.loading = true;
    const formattedData = this.formatReservationData(this.form);
    this.ghorarioService.updateReservation(formattedData).subscribe({
      next: () => {
        this.loadReservations();
        this.isEditing = false;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao atualizar reserva:', err);
        this.loading = false;
      }
    });
  }

  // Método para excluir uma reserva
  deleteReservation(id: number): void {
    this.loading = true;
    this.ghorarioService.deleteReservation(id).subscribe({
      next: () => {
        this.loadReservations();
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao excluir reserva:', err);
        this.loading = false;
      }
    });
  }

  // Método para cancelar a edição
  cancel(): void {
    this.isEditing = false;
    this.resetForm();
  }

  // Método para resetar o formulário
  resetForm(): void {
    this.form = { teacher: null, subject: null, time: null, date: '', room: null, course: null };
  }

  // Método para formatar os dados antes de enviar
  formatReservationData(reservationData: ReservationForm): any {
    const finalData = {
      teacher: Number(reservationData.teacher),
      subject: Number(reservationData.subject),
      time: Number(reservationData.time),
      date: reservationData.date, // Date continua como string
      room: Number(reservationData.room),
      course: Number(reservationData.course),
    };

    console.log('Dados enviados para criar ou atualizar reserva:', JSON.stringify(finalData, null, 2));
    return finalData;
  }
}

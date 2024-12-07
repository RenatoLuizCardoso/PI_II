import { Component, OnInit } from '@angular/core';
import { ReservarService } from '../../serv/prof/reservar.service';

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
  selector: 'app-reservar',
  templateUrl: './reservar.component.html',
  styleUrls: ['./reservar.component.css']
})
export class ReservarComponent implements OnInit {
  reservations: Reservation[] = [];
  teachers: any[] = [];
  subjects: any[] = [];
  times: any[] = [];
  rooms: any[] = [];
  courses: any[] = [];

  form: ReservationForm = { teacher: null, subject: null, time: null, date: '', room: null, course: null };
  isEditing: boolean = false;
  loading: boolean = false;

  constructor(private reservarService: ReservarService) {}

  ngOnInit(): void {
    this.loadReservations();
    this.loadAuxiliaryData();
  }

  // Método para carregar as reservas
  loadReservations(): void {
    this.loading = true;
    this.reservarService.getReservations().subscribe({
      next: (data) => {
        this.reservations = Array.isArray(data) ? data : [];
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar reservas:', err);
        this.loading = false;
      }
    });
  }

  // Método para carregar os dados auxiliares (listas dinâmicas)
  loadAuxiliaryData(): void {
    this.reservarService.getTeachers().subscribe(data => {
      console.log('Teachers:', data);
      // Verifica se a resposta é um objeto (não um array) e transforma em array
      this.teachers = Array.isArray(data) ? data : [data]; // Aqui tratamos o valor como array
    });
    this.reservarService.getSubjects().subscribe(data => {
      console.log('Subjects:', data);
      this.subjects = Array.isArray(data) ? data : [];
    });
    this.reservarService.getTimes().subscribe(data => {
      console.log('Times:', data);
      this.times = Array.isArray(data) ? data : [];
    });
    this.reservarService.getRooms().subscribe(data => {
      console.log('Rooms:', data);
      this.rooms = Array.isArray(data) ? data : [];
    });
    this.reservarService.getCourses().subscribe(data => {
      console.log('Courses:', data);
      this.courses = Array.isArray(data) ? data : [];
    });
  }

  // Método para criar uma nova reserva
  createReservation(): void {
    this.loading = true;
    const formattedData = this.formatReservationData(this.form);
    console.log('Dados enviados para criação de reserva:', formattedData);

    this.reservarService.createReservation(formattedData).subscribe({
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
    this.reservarService.updateReservation(formattedData).subscribe({
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
    this.reservarService.deleteReservation(id).subscribe({
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
      teacher: { teacherId: Number(reservationData.teacher) },
      subject: { subjectId: Number(reservationData.subject) },
      time: { timeId: Number(reservationData.time) },
      date: reservationData.date, // Date continua como string
      room: { roomId: Number(reservationData.room) },
      course: { courseId: Number(reservationData.course) }
    };

    console.log('Dados enviados para criar ou atualizar reserva:', JSON.stringify(finalData, null, 2));
    return finalData;
  }
}

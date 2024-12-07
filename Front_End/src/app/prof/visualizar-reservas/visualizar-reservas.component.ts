import { Component, OnInit } from '@angular/core';
import { ReservarService } from '../../serv/prof/reservar.service';

interface ReservationForm {
  teacher: number | null;
  subject: number | null;
  time: number | null;
  date: string;
  room: number | null;
  course: number | null;
  teacherName?: string;
  courseName?: string;
  timeName?: string;
  roomName?: string;
}

@Component({
  selector: 'app-visualizar-reservas',
  templateUrl: './visualizar-reservas.component.html',
  styleUrls: ['./visualizar-reservas.component.css']
})
export class VisualizarReservasComponent implements OnInit {
  reservations: any[] = [];
  teachers: any[] = [];
  subjects: any[] = [];
  selectedReservation: any = null;
  times: any[] = [];
  rooms: any[] = [];
  courses: any[] = [];
  searchTerm: string = '';
  form: ReservationForm = { teacher: null, subject: null, time: null, date: '', room: null, course: null };
  isEditing: boolean = false;
  loading: boolean = false;

  constructor(private reservarService: ReservarService) {}

  ngOnInit(): void {
    this.loadReservations();
    this.loadAuxiliaryData();
  }

  loadReservations(): void {
    this.loading = true;
    this.reservarService.getReservations().subscribe({
      next: (data) => {
        this.reservations = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar reservas:', err);
        this.loading = false;
        alert('Erro ao carregar as reservas. Por favor, tente novamente.');
      },
    });
  }

  // Método melhorado para pesquisa com tratamento de undefined e case-insensitive
  get filteredReservations(): any[] {
    return this.reservations.filter((reservation) =>
      (reservation.teacher?.toLowerCase() ?? '').includes(this.searchTerm.toLowerCase()) ||
      (reservation.course?.toLowerCase() ?? '').includes(this.searchTerm.toLowerCase())
    );
  }

  loadAuxiliaryData(): void {
    this.reservarService.getTeachers().subscribe({
      next: (data) => (this.teachers = data),
      error: (err) => {
        console.error('Erro ao carregar professores:', err);
        alert('Erro ao carregar professores');
      },
    });
    this.reservarService.getSubjects().subscribe({
      next: (data) => (this.subjects = data),
      error: (err) => {
        console.error('Erro ao carregar matérias:', err);
        alert('Erro ao carregar matérias');
      },
    });
    this.reservarService.getTimes().subscribe({
      next: (data) => (this.times = data),
      error: (err) => {
        console.error('Erro ao carregar horários:', err);
        alert('Erro ao carregar horários');
      },
    });
    this.reservarService.getRooms().subscribe({
      next: (data) => (this.rooms = data),
      error: (err) => {
        console.error('Erro ao carregar salas:', err);
        alert('Erro ao carregar salas');
      },
    });
    this.reservarService.getCourses().subscribe({
      next: (data) => (this.courses = data),
      error: (err) => {
        console.error('Erro ao carregar cursos:', err);
        alert('Erro ao carregar cursos');
      },
    });
  }

  editReservation(reservation: any): void {
    this.selectedReservation = reservation;
    this.isEditing = true;

    // Verificar se "this.teachers" é um array e se "reservation.teacher" existe
    const teacher = Array.isArray(this.teachers) ? this.teachers.find((t) => t.id === reservation.teacher) : null;
    const course = Array.isArray(this.courses) ? this.courses.find((c) => c.id === reservation.course) : null;
    const time = Array.isArray(this.times) ? this.times.find((t) => t.id === reservation.time) : null;
    const room = Array.isArray(this.rooms) ? this.rooms.find((r) => r.id === reservation.room) : null;

    // Atualizando o formulário com os dados completos, com fallback caso não encontre os dados
    this.form = {
      teacher: reservation.teacher,
      subject: reservation.subject,
      time: reservation.time,
      date: reservation.date,
      room: reservation.room,
      course: reservation.course,
      teacherName: teacher ? teacher.name : 'Não encontrado',
      courseName: course ? course.name : 'Não encontrado',
      timeName: time ? time.name : 'Não encontrado',
      roomName: room ? room.name : 'Não encontrado',
    };
  }


  cancel(): void {
    this.isEditing = false;
    this.selectedReservation = null;
    this.form = { teacher: null, subject: null, time: null, date: '', room: null, course: null };
  }

  deleteReservation(id: number): void {
    if (confirm('Tem certeza de que deseja excluir esta reserva?')) {
      this.reservarService.deleteReservation(id).subscribe({
        next: () => {
          alert('Reserva excluída com sucesso!');
          this.loadReservations();  // Recarregar as reservas após a exclusão
        },
        error: (err) => {
          console.error('Erro ao excluir reserva:', err);
          alert('Erro ao excluir reserva');
        }
      });
    }
  }
}

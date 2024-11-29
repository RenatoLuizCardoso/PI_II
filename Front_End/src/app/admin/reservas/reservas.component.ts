import { Component, OnInit } from '@angular/core';
import { GhorarioService } from '../../serv/admin/ghorario.service';

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
  selector: 'app-reservas',
  templateUrl: './reservas.component.html',
  styleUrls: ['./reservas.component.css'],
})
export class ReservasComponent implements OnInit {
  reservations: any[] = [];
  teachers: any[] = [];
  subjects: any[] = [];
  times: any[] = [];
  rooms: any[] = [];
  courses: any[] = [];
  searchTerm: string = '';
  form: ReservationForm = { teacher: null, subject: null, time: null, date: '', room: null, course: null };
  isEditing: boolean = false;
  loading: boolean = false;

  constructor(private ghorarioService: GhorarioService) {}

  ngOnInit(): void {
    this.loadReservations();
    this.loadAuxiliaryData();
  }

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
      },
    });
  }

  get filteredReservations(): any[] {
    return this.reservations.filter((reservation) =>
      (reservation.teacherName?.toLowerCase() || '').includes(this.searchTerm.toLowerCase()) ||
      (reservation.courseName?.toLowerCase() || '').includes(this.searchTerm.toLowerCase())
    );
  }

  loadAuxiliaryData(): void {
    this.ghorarioService.getTeachers().subscribe((data) => (this.teachers = data));
    this.ghorarioService.getSubjects().subscribe((data) => (this.subjects = data));
    this.ghorarioService.getTimes().subscribe((data) => (this.times = data));
    this.ghorarioService.getRooms().subscribe((data) => (this.rooms = data));
    this.ghorarioService.getCourses().subscribe((data) => (this.courses = data));
  }

  editReservation(reservation: any): void {
  this.isEditing = true;

  // Mapeamento para obter detalhes completos
  const teacher = this.teachers.find((t) => t.id === reservation.teacher) || {};
  const course = this.courses.find((c) => c.id === reservation.course) || {};
  const time = this.times.find((t) => t.id === reservation.time) || {};
  const room = this.rooms.find((r) => r.id === reservation.room) || {};

  this.form = {
    teacher: reservation.teacher,
    subject: reservation.subject,
    time: reservation.time,
    date: reservation.date,
    room: reservation.room,
    course: reservation.course,
    teacherName: teacher.name || 'Não encontrado',
    courseName: course.name || 'Não encontrado',
    timeName: time.name || 'Não encontrado',
    roomName: room.name || 'Não encontrado',
  };
}


  cancel(): void {
    this.isEditing = false;
    this.form = { teacher: null, subject: null, time: null, date: '', room: null, course: null };
  }
 deleteReservation(id: number): void {
  if (confirm('Tem certeza de que deseja excluir esta reserva?')) {
    this.ghorarioService.deleteReservation(id).subscribe({
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

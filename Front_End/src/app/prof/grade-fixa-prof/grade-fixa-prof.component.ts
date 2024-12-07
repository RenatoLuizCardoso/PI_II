import { Component, OnInit } from '@angular/core';
import { GradeFixaProfService } from '../../serv/prof/grade-fixa-prof.service'; // Certifique-se de ajustar o caminho do serviço
import { Schedule, Teacher, Subject, Room, Time, Course, WeekDay } from '../../grade-fixa.types'; // Importando o tipo WeekDay

@Component({
  selector: 'app-grade-fixa-prof',
  templateUrl: './grade-fixa-prof.component.html',
  styleUrls: ['./grade-fixa-prof.component.css']
})
export class GradeFixaProfComponent implements OnInit {
  schedules: Schedule[] = [];
  teachers: Teacher[] = [];
  subjects: Subject[] = [];
  times: Time[] = [];
  weekDay: WeekDay[] = [];
  rooms: Room[] = [];
  courses: Course[] = []; // Lista de cursos
  newSchedule: Partial<Schedule> = {};
  
  // Variável para armazenar o termo de pesquisa (agora usado para armazenar o curso selecionado)
  searchTerm: string = '';
  filteredTimes: Time[] = []; // Lista de horários filtrados

  constructor(private gradeFixaServiceProf: GradeFixaProfService) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.gradeFixaServiceProf.getSchedules().subscribe((data) => {
      console.log('Schedules recebidos:', data);
      this.schedules = data;
    });
    this.gradeFixaServiceProf.getTimes().subscribe((data) => {
      console.log('Times recebidos:', data);
      this.times = data;
      this.filteredTimes = data; // Inicialmente, todos os horários são exibidos
    });
    this.gradeFixaServiceProf.getCourses().subscribe((data) => {
      console.log('Cursos recebidos:', data);
      this.courses = data; // Preenche a lista de cursos
    });
  }

  // Função para exibir os horários de timeId 11 a 16 (Tarde)
  showTimes11to16(): void {
    console.log('Mostrando horários de timeId 11 a 16');
    this.filteredTimes = this.times.filter(time => time.timeId >= 11 && time.timeId <= 16);
  }

  // Função para exibir os horários de timeId 27 a 32 (Manhã)
  showTimes27to32(): void {
    console.log('Mostrando horários de timeId 27 a 32');
    this.filteredTimes = this.times.filter(time => time.timeId >= 27 && time.timeId <= 32);
  }

  // Função para exibir os horários de timeId 33 a 38 (Noite)
  showTimes33to38(): void {
    console.log('Mostrando horários de timeId 33 a 38');
    this.filteredTimes = this.times.filter(time => time.timeId >= 33 && time.timeId <= 38);
  }

  // Função para limpar o campo de pesquisa
  clearSearch(): void {
    this.searchTerm = '';
  }

  createSchedule(): void {
    console.log('Tentando criar agendamento com os seguintes dados:', this.newSchedule);

    // Enviando o novo agendamento com o campo weekDay
    this.gradeFixaServiceProf.createSchedule(this.newSchedule).subscribe(
      () => {
        console.log('Agendamento criado com sucesso!');
        this.fetchData();
        this.newSchedule = {}; // Limpar o formulário
      },
      (error) => {
        console.error('Erro ao criar agendamento:', error);
      }
    );
  }

  deleteSchedule(id: number): void {
    this.gradeFixaServiceProf.deleteSchedule(id).subscribe(() => this.fetchData());
  }
}

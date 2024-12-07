import { Component, OnInit } from '@angular/core';
import { GradeFixaService } from '../../serv/admin/grade-fixa.service'; // Certifique-se de ajustar o caminho do serviço
import { Schedule, Teacher, Subject, Room, Time, Course } from '../../grade-fixa.types'; // Importando os tipos

@Component({
  selector: 'app-grade-fixa',
  templateUrl: './grade-fixa.component.html',
  styleUrls: ['./grade-fixa.component.css']
})
export class GradeFixaComponent implements OnInit {
  schedules: Schedule[] = [];
  times: Time[] = [];
  courses: Course[] = [];
  filteredTimes: Time[] = [];
  searchTerm: string = '';
  scheduleIdToDelete: number | null = null; // Variável para armazenar o ID do agendamento que será excluído

  constructor(private gradeFixaService: GradeFixaService) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    // Carrega os dados de agendamentos, horários e cursos
    this.gradeFixaService.getSchedules().subscribe((data) => {
      console.log('Schedules recebidos:', data);
      this.schedules = data;
    });
    this.gradeFixaService.getTimes().subscribe((data) => {
      console.log('Times recebidos:', data);
      this.times = data;
      this.filteredTimes = data; // Inicialmente, exibe todos os horários
    });
    this.gradeFixaService.getCourses().subscribe((data) => {
      console.log('Cursos recebidos:', data);
      this.courses = data; // Preenche a lista de cursos
    });
  }

  // Função para exibir os horários de timeId 11 a 16
  showTimes11to16(): void {
    console.log('Mostrando horários de timeId 11 a 16');
    this.filteredTimes = this.times.filter(time => time.timeId >= 11 && time.timeId <= 16);
  }

  // Função para exibir os horários de timeId 27 a 32
  showTimes27to32(): void {
    console.log('Mostrando horários de timeId 27 a 32');
    this.filteredTimes = this.times.filter(time => time.timeId >= 27 && time.timeId <= 32);
  }

  // Função para exibir os horários de timeId 33 a 38
  showTimes33to38(): void {
    console.log('Mostrando horários de timeId 33 a 38');
    this.filteredTimes = this.times.filter(time => time.timeId >= 33 && time.timeId <= 38);
  }

  // Função para alternar a visibilidade do botão de apagar
  toggleDeleteButton(scheduleId: number): void {
    if (this.scheduleIdToDelete === scheduleId) {
      this.scheduleIdToDelete = null; // Se o mesmo agendamento for clicado novamente, oculta o botão
    } else {
      this.scheduleIdToDelete = scheduleId; // Exibe o botão de excluir para o agendamento clicado
    }
  }

  // Função para deletar o agendamento
  deleteSchedule(scheduleId: number, event: Event): void {
    event.stopPropagation(); // Impede que o clique no botão acione o clique na célula

    if (confirm('Tem certeza de que deseja excluir este agendamento?')) {
      this.gradeFixaService.deleteSchedule(scheduleId).subscribe(
        () => {
          // Após a exclusão, atualiza a lista de agendamentos
          this.fetchData();
          alert('Agendamento excluído com sucesso!');
        },
        (error) => {
          console.error('Erro ao excluir o agendamento:', error);
          alert('Houve um erro ao excluir o agendamento. Tente novamente mais tarde.');
        }
      );
    }
  }

  // Função para verificar se a célula está sendo destacada (hover)
  isHovered(scheduleId: number): boolean {
    return this.scheduleIdToDelete === scheduleId; // Se o ID do agendamento for igual ao clicado, aplica o highlight
  }
}

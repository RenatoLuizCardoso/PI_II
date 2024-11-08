import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Aula {
  materia: string;
  aula: string;
  horario: string;
}

interface Horarios {
  curso: string;
  semestre: string;
  dias: {
    [key: string]: Aula[];
  };
}

interface Cursos {
  cursos: Horarios[];
}

@Component({
  selector: 'app-grade-horario',
  templateUrl: './grade-horario.component.html',
  styleUrls: ['./grade-horario.component.css']
})
export class GradeHorarioComponent implements OnInit {
  cursos: Horarios[] = [];
  horarios: Horarios | null = null;

  // Variáveis para a navegação dos meses e semanas
  diasDoMes: number[] = [];
  nomeMes: string = ''; // Nome do mês (ex: "Novembro 2024")
  semanaAtual: number = 1;
  semanas: number[][] = []; // Guardar as semanas com os dias completos, incluindo os dias do próximo mês

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadCursos();
    this.carregarDiasDoMes();  // Carregar os dias do mês inicialmente
  }

  loadCursos(): void {
    this.http.get<Horarios[]>('/assets/cursos.json').subscribe(data => {
      this.cursos = data;
      this.horarios = this.cursos[0]; // Seleciona o primeiro curso por padrão
    });
  }

  selecionarCurso(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const index = target.selectedIndex;
    this.horarios = this.cursos[index]; // Atualiza a tabela com o curso selecionado
  }

  getMateriaParaHorario(dia: string, horario: string): string {
    if (!this.horarios || !this.horarios.dias[dia]) return '';

    const aulas = this.horarios.dias[dia];
    const aula = aulas.find(a => a.horario === horario);
    return aula ? aula.materia : ''; // Retorna a matéria ou uma string vazia
  }

  carregarDiasDoMes(): void {
    // Lógica para carregar todos os dias do mês, considerando o mês atual
    const currentDate = new Date();
    const numDias = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 0).getDate(); // Pega o último dia do mês
    const firstDayOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1).getDay(); // 0 = Domingo, 1 = Segunda-feira, etc.
    
    this.diasDoMes = Array.from({ length: numDias }, (_, i) => i + 1); // Preenche os dias do mês atual
    this.nomeMes = currentDate.toLocaleString('pt-BR', { month: 'long', year: 'numeric' }); // Nome do mês
    
    this.criarSemanas(firstDayOfMonth, numDias);
  }

  criarSemanas(firstDayOfMonth: number, numDias: number): void {
    let dias: number[] = this.diasDoMes;
    let weeks: number[][] = [];
    let currentWeek: number[] = [];

    // Adicionando dias do próximo mês caso a semana não tenha 7 dias completos
    const lastDayOfMonth = new Date(new Date().getFullYear(), new Date().getMonth() + 1, 0).getDay();
    const remainingDays = 7 - (lastDayOfMonth + 1);
    if (remainingDays !== 0) {
      let nextMonthDays = Array.from({ length: remainingDays }, (_, i) => i + 1);
      dias = [...dias, ...nextMonthDays];
    }

    // Organiza os dias do mês em semanas de 7 dias
    for (let i = 0; i < dias.length; i++) {
      if (currentWeek.length < 7) {
        currentWeek.push(dias[i]);
      } else {
        weeks.push(currentWeek);
        currentWeek = [dias[i]];
      }
    }
    if (currentWeek.length) {
      weeks.push(currentWeek); // Adiciona a última semana
    }
    this.semanas = weeks;
  }

  // Função para navegar entre as semanas
  navegarSemana(direcao: number): void {
    this.semanaAtual += direcao;
    if (this.semanaAtual < 1) this.semanaAtual = 1;  // Limite para não passar para semana negativa
    if (this.semanaAtual > this.semanas.length) this.semanaAtual = this.semanas.length;  // Limite para não passar da última semana
  }

  // Verifica se o dia está dentro da semana atual (1-7, 8-14, etc.)
  isDiaNaSemana(index: number): boolean {
    const inicioSemana = (this.semanaAtual - 1) * 7;
    const fimSemana = inicioSemana + 6;
    return index >= inicioSemana && index <= fimSemana;
  }

  getClassForDay(day: number): string {
    const currentMonthDays = new Date().getDate();
    return day > currentMonthDays ? 'transicao' : ''; // Adiciona a classe 'transicao' para dias de próximo mês
  }
}

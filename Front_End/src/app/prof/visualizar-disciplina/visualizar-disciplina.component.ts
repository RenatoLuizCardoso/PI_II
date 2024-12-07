import { Component, OnInit } from '@angular/core';
import { PerfilService } from '../../serv/prof/perfil.service';  // Importe o serviço

@Component({
  selector: 'app-visualizar-disciplina',
  templateUrl: './visualizar-disciplina.component.html',
  styleUrls: ['./visualizar-disciplina.component.css']
})
export class VisualizarDisciplinaComponent implements OnInit {
  loading = true;
  error = '';
  professor: any = null;  // Para armazenar os dados do professor
  disciplinas: any[] = [];  // Lista de disciplinas filtradas
  allDisciplinas: any[] = [];  // Todas as disciplinas da API

  constructor(private perfilService: PerfilService) {}

  ngOnInit(): void {
    this.carregarProfessorEdisciplinas();
  }

  // Método para carregar o professor e suas disciplinas
  carregarProfessorEdisciplinas(): void {
    // Carregar o perfil do professor
    this.perfilService.getProfile().subscribe({
      next: (professor) => {
        console.log('Professor carregado:', professor);
        this.professor = professor;

        // Agora que temos o professor, vamos carregar as disciplinas
        this.carregarDisciplinas();
      },
      error: (err) => {
        console.error('Erro ao carregar o professor:', err);
        this.error = 'Erro ao carregar o professor';
        this.loading = false;
      }
    });
  }

  // Método para carregar todas as disciplinas
  carregarDisciplinas(): void {
    this.perfilService.getSubjects().subscribe({
      next: (response) => {
        console.log('Resposta da API (disciplinas):', response);
        this.allDisciplinas = response;

        // Filtrar disciplinas que correspondem aos teacherSubjects do professor
        if (this.professor && this.professor.teacherSubjects) {
          this.disciplinas = this.allDisciplinas.filter(disciplina => 
            this.professor.teacherSubjects.includes(disciplina.subjectId)
          );
        }
        
        console.log('Disciplinas filtradas:', this.disciplinas);
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar disciplinas:', err);
        this.error = 'Erro ao carregar disciplinas';
        this.loading = false;
      }
    });
  }
}

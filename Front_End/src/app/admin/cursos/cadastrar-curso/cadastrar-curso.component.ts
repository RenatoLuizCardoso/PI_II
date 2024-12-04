import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CcursoService } from '../../../serv/admin/ccurso.service';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-cadastrar-curso',
  templateUrl: './cadastrar-curso.component.html',
  styleUrls: ['./cadastrar-curso.component.css']
})
export class CadastrarCursoComponent implements OnInit {
  registerForm!: FormGroup;
  registerError: boolean = false;
  registerSuccess: boolean = false;
  disciplines: any[] = [];  // Para armazenar as disciplinas carregadas
  selectedDisciplines: { subjectId: number }[] = [];  // Para armazenar as disciplinas selecionadas como objetos
  disciplineSelectionOpen: boolean = false;  // Controla se o modal está aberto

  constructor(
    private formBuilder: FormBuilder,
    private ccursoService: CcursoService,
    private router: Router,
    private titleService: Title
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Cadastro de Cursos');
    this.registerForm = this.formBuilder.group({
      courseName: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Letras e espaços
      courseSemester: ['', [Validators.required]],
      coursePeriod: ['', Validators.required],
      courseSubjects: ['']  // Campo de matérias será preenchido ao enviar o formulário
    });

    this.loadDisciplines();  // Carrega as disciplinas ao iniciar
  }

  get f() { return this.registerForm.controls; }

  // Função para carregar as disciplinas
  loadDisciplines(): void {
    this.ccursoService.getDisciplines().subscribe(
      (response) => {
        this.disciplines = response;  // Armazena as disciplinas no array
        console.log('Disciplinas carregadas:', this.disciplines);
      },
      (error) => {
        console.error('Erro ao carregar as disciplinas:', error);
      }
    );
  }

  // Função para abrir/fechar o modal de seleção de disciplinas
  toggleDisciplineSelection(): void {
    this.disciplineSelectionOpen = !this.disciplineSelectionOpen;
  }

  // Função chamada ao alterar a seleção de uma disciplina
  onDisciplineChange(event: any): void {
    const subjectId = event.target.value;
    if (event.target.checked) {
      // Adiciona o objeto { subjectId: X } ao invés de só o ID
      this.selectedDisciplines.push({ subjectId: Number(subjectId) });
    } else {
      // Remove o objeto { subjectId: X } do array
      this.selectedDisciplines = this.selectedDisciplines.filter(discipline => discipline.subjectId !== Number(subjectId));
    }
    console.log('Disciplinas selecionadas:', this.selectedDisciplines);
  }

  // Função para confirmar a seleção de disciplinas e fechar o modal
  confirmDisciplineSelection(): void {
    console.log('Disciplinas confirmadas:', this.selectedDisciplines);
    this.toggleDisciplineSelection();  // Fecha o modal
  }

  onSubmit(): void {
    if (this.registerForm.invalid || this.selectedDisciplines.length === 0) {
      this.registerError = true;
      return;
    }

    // Monta os dados do curso com a nova estrutura para "courseSubjects"
    const courseData = {
      courseName: this.registerForm.value.courseName,
      courseSemester: this.registerForm.value.courseSemester,
      coursePeriod: this.registerForm.value.coursePeriod,
      // Mantém o formato do JSON com objetos { subjectId: X }
      courseSubjects: this.selectedDisciplines
    };

    // Envia os dados para o backend
    this.ccursoService.registerCurso(courseData).subscribe(
      (response) => {
        console.log('Formulário enviado com sucesso', response);
        this.registerSuccess = true;
        this.registerForm.reset();
        this.selectedDisciplines = [];
        this.registerError = false;
        setTimeout(() => this.registerSuccess = false, 5000);
        this.router.navigate(['/admin/gerenciar_curso']);
      },
      (error) => {
        console.error('Erro ao enviar o formulário', error);
        this.registerError = true;
      }
    );
  }
}

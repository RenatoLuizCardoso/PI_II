import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CcursoService } from '../../../serv/admin/ccurso.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-editar-curso',
  templateUrl: './editar-curso.component.html',
  styleUrls: ['./editar-curso.component.css']
})
export class EditarCursoComponent implements OnInit {
  cursoForm: FormGroup;
  mensagemSucesso: boolean = false;
  disciplines: any[] = [];
  selectedDisciplines: number[] = [];
  disciplineSelectionOpen: boolean = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private ccursoService: CcursoService,
    private titleService: Title
  ) {
    this.cursoForm = this.fb.group({
      courseName: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]],
      courseSemester: ['', [Validators.required]],
      coursePeriod: ['', Validators.required],
      courseSubjects: [''] // Para armazenar disciplinas selecionadas
    });
  }

  ngOnInit(): void {
    console.log('Componente de edição carregado.');
    this.carregarCurso();
    this.carregarDisciplinas();
    this.titleService.setTitle('Edição de Curso');
  }

  get f() { return this.cursoForm.controls; }

  carregarCurso() {
    const id = this.route.snapshot.paramMap.get('id');
    console.log('ID do curso recebido:', id);
    if (id) {
      this.ccursoService.getCursoById(id).subscribe(
        data => {
          console.log('Dados do curso recebidos:', data);
          // Atualizando o formulário com os dados corretos
          this.cursoForm.patchValue({
            courseName: data.courseName, // Nome do curso
            courseSemester: data.courseSemester,
            coursePeriod: data.coursePeriod,
          });
  
          // Aqui estamos mapeando corretamente as disciplinas
          this.selectedDisciplines = data.subjects.map((subject: string) => subject); // Assumindo que 'subjects' é um array de strings.
          console.log('Disciplinas selecionadas carregadas:', this.selectedDisciplines);
        },
        error => {
          console.error('Erro ao carregar curso:', error);
        }
      );
    }
  }
  

  carregarDisciplinas() {
    console.log('Carregando lista de disciplinas...');
    this.ccursoService.getDisciplines().subscribe(
      (response) => {
        console.log('Disciplinas recebidas:', response);
        this.disciplines = response;
      },
      (error) => {
        console.error('Erro ao carregar disciplinas:', error);
      }
    );
  }

  toggleDisciplineSelection(): void {
    this.disciplineSelectionOpen = !this.disciplineSelectionOpen;
    console.log('Seleção de disciplinas aberta:', this.disciplineSelectionOpen);
  }

  onDisciplineChange(event: any): void {
    const subjectId = event.target.value;
    if (event.target.checked) {
      this.selectedDisciplines.push(Number(subjectId));
    } else {
      this.selectedDisciplines = this.selectedDisciplines.filter(id => id !== Number(subjectId));
    }
    console.log('Disciplinas selecionadas atualizadas:', this.selectedDisciplines);
  }

  confirmDisciplineSelection(): void {
    this.toggleDisciplineSelection();
    console.log('Seleção de disciplinas confirmada:', this.selectedDisciplines);
  }

  salvar() {
    console.log('Tentando salvar curso...');
    if (this.cursoForm.invalid || this.selectedDisciplines.length === 0) {
      console.warn('Formulário inválido ou disciplinas não selecionadas:', this.cursoForm.value, this.selectedDisciplines);
      this.cursoForm.markAllAsTouched();
      return;
    }

    const cursoAtualizado = {
      courseName: this.cursoForm.value.courseName,
      courseSemester: this.cursoForm.value.courseSemester,
      coursePeriod: this.cursoForm.value.coursePeriod,
      courseSubjects: this.selectedDisciplines
    };

    console.log('Dados do curso a ser enviado:', cursoAtualizado);

    this.ccursoService.updateCurso(cursoAtualizado).subscribe(
      () => {
        console.log('Curso atualizado com sucesso.');
        this.mensagemSucesso = true;
        setTimeout(() => {
          this.mensagemSucesso = false;
          this.router.navigate(['/admin/gerenciar_curso']);
        }, 3000);
      },
      error => {
        console.error('Erro ao atualizar curso:', error);
      }
    );
  }
}

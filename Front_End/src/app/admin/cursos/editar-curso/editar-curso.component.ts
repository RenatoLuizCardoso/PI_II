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
  selectedDisciplines: number[] = [];  // Aqui garantimos que seja um array de números
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
      courseSubjects: ['']  // Para armazenar disciplinas selecionadas
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
  
          // Agora, vamos garantir que as disciplinas sejam mapeadas para IDs.
          // A suposição aqui é que você tem um array `disciplines` contendo
          // os IDs das disciplinas no backend, e precisa fazer o mapeamento
          // de nome para ID.
          this.selectedDisciplines = data.subjects.map((subjectName: string) => {
            const foundDiscipline = this.disciplines.find(d => d.subjectName === subjectName);
            return foundDiscipline ? foundDiscipline.subjectId : null;
          }).filter((id: number | null) => id !== null);  // Tipando explicitamente o id como number ou null
          
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
        this.disciplines = response;  // A lista de disciplinas com nome e ID
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
    const subjectId = Number(event.target.value);  // Garantindo que seja um número
    if (event.target.checked) {
      this.selectedDisciplines.push(subjectId);
    } else {
      this.selectedDisciplines = this.selectedDisciplines.filter(id => id !== subjectId);
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

    // Recuperando o ID do curso da rota
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      console.error('ID do curso não encontrado');
      return;
    }

    // Ajustando a estrutura das disciplinas para o formato correto
    const courseSubjects = this.selectedDisciplines.map(disciplineId => ({
      subjectId: Number(disciplineId) // Garantindo que seja um número
    }));

    // Criando o objeto que será enviado ao backend, incluindo o courseId
    const cursoAtualizado = {
      courseId: id,  // Incluindo o ID do curso
      courseName: this.cursoForm.value.courseName,  // Nome do curso
      courseSemester: this.cursoForm.value.courseSemester, // Semestre
      coursePeriod: this.cursoForm.value.coursePeriod,  // Período
      courseSubjects: courseSubjects  // Disciplinas no formato correto
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

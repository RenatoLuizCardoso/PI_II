import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProfessoresService } from '../../../serv/admin/professores.service';
import { CdisciplinaService } from '../../../serv/admin/cdisciplina.service';

@Component({
  selector: 'app-editar-professor',
  templateUrl: './editar-professor.component.html',
  styleUrls: ['./editar-professor.component.css']
})
export class EditarProfessorComponent implements OnInit {
  professorForm!: FormGroup;
  mensagemSucesso: boolean = false;
  disciplinas: any[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private professoresService: ProfessoresService,
    private cdisciplinaService: CdisciplinaService
  ) {}

  ngOnInit(): void {
    this.professorForm = this.fb.group({
      teacherName: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]],
      teacherArea: ['', [Validators.required]],
      researchLine: ['', [Validators.required]],
      institutionalEmail: ['', [Validators.required, Validators.email]],
      personalEmail: ['', [Validators.email]],
      personalPhone: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      businessPhone: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      teacherSubjects: [null, [Validators.required]],
    });

    this.carregarDisciplinas();
    this.carregarProfessor();
  }

  get f() {
    return this.professorForm.controls;
  }

  carregarDisciplinas() {
    this.cdisciplinaService.getDisciplines().subscribe(
      data => (this.disciplinas = data),
      error => console.error('Erro ao carregar disciplinas:', error)
    );
  }

  carregarProfessor() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.professoresService.getProfessorById(id).subscribe(
        data => this.professorForm.patchValue(data),
        error => console.error('Erro ao carregar professor:', error)
      );
    }
  }

  salvar() {
    if (this.professorForm.valid) {
      const professorAtualizado = this.professorForm.value;
      this.professoresService.updateProfessor(professorAtualizado).subscribe(
        () => {
          this.mensagemSucesso = true;
          setTimeout(() => {
            this.mensagemSucesso = false;
            this.router.navigate(['/admin/gerenciar_professor']);
          }, 3000);
        },
        error => console.error('Erro ao atualizar professor:', error)
      );
    } else {
      this.professorForm.markAllAsTouched();
    }
  }
}

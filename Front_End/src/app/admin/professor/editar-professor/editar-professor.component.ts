import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProfessoresService } from '../../../serv/admin/professores.service';

@Component({
  selector: 'app-editar-professor',
  templateUrl: './editar-professor.component.html',
  styleUrls: ['./editar-professor.component.css']
})
export class EditarProfessorComponent implements OnInit {
  professorForm: FormGroup;
  mensagemSucesso: boolean = false;
  cursos: any[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private professoresService: ProfessoresService
  ) {
    this.professorForm = this.fb.group({
      id: [{ value: '', disabled: true }],
      name: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Permite letras, acentos e espaços
      emailI: ['', [Validators.required, Validators.email]],
      senha: ['', [Validators.required, Validators.minLength(6)]],
      emailP: ['', [Validators.email]],
      tel: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Permite apenas números
      curso: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.carregarProfessor();
  }
  

  carregarProfessor() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.professoresService.getProfessorById(id).subscribe(
        data => {
          this.professorForm.patchValue(data);
        },
        error => {
          console.error('Erro ao carregar professor: ', error);
        }
      );
    }
  }

  salvar() {
    if (this.professorForm.valid) {
      const professorAtualizado = this.professorForm.getRawValue();
      this.professoresService.updateProfessor(professorAtualizado).subscribe(
        () => {
          this.mensagemSucesso = true;
          setTimeout(() => {
            this.mensagemSucesso = false;
            this.router.navigate(['/admin/gerenciar_professor']);
          }, 3000);
        },
        error => {
          console.error('Erro ao atualizar professor: ', error);
        }
      );
    } else {
      this.professorForm.markAllAsTouched();
    }
  }
}

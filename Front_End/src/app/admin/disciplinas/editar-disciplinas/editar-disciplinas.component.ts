import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CdisciplinaService } from '../../../serv/admin/cdisciplina.service';

@Component({
  selector: 'app-editar-disciplinas',
  templateUrl: './editar-disciplinas.component.html',
  styleUrls: ['./editar-disciplinas.component.css']
})
export class EditarDisciplinasComponent implements OnInit {
  disciplinaForm: FormGroup;
  mensagemSucesso: boolean = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private cdisciplinaService: CdisciplinaService
  ) {
    this.disciplinaForm = this.fb.group({
      id: [{ value: '', disabled: true }],
      disciplineName: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9 ]*$')]],  // Validação para letras, números e espaços
      hour: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],  // Validação para número positivo
      professor: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.carregarDiscipline();
  }

  carregarDiscipline() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.cdisciplinaService.getDisciplineById(id).subscribe(
        data => {
          this.disciplinaForm.patchValue(data);
        },
        error => {
          console.error('Erro ao carregar disciplina: ', error);
        }
      );
    }
  }

  salvar() {
    if (this.disciplinaForm.valid) {
      const disciplinaAtualizada = this.disciplinaForm.getRawValue();
      this.cdisciplinaService.updateDiscipline(disciplinaAtualizada).subscribe(
        () => {
          this.mensagemSucesso = true;
          setTimeout(() => {
            this.mensagemSucesso = false;
            this.router.navigate(['/admin/gerenciar_disc']);
          }, 3000);
        },
        error => {
          console.error('Erro ao atualizar disciplina: ', error);
        }
      );
    }
  }

  get f() { return this.disciplinaForm.controls; }
}

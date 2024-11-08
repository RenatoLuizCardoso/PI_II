import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CcursoService } from '../../../serv/admin/ccurso.service';

@Component({
  selector: 'app-editar-curso',
  templateUrl: './editar-curso.component.html',
  styleUrls: ['./editar-curso.component.css']
})
export class EditarCursoComponent implements OnInit {
  cursoForm: FormGroup;
  mensagemSucesso: boolean = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private ccursoService: CcursoService
  ) {
    this.cursoForm = this.fb.group({
      id: [{ value: '', disabled: true }],
      nameCourse: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Letras e espaços
      semester: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Números apenas
      period: ['', Validators.required],
      disciplina: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.carregarCurso();
  }

  carregarCurso() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.ccursoService.getCursoById(id).subscribe(
        data => {
          this.cursoForm.patchValue(data);
        },
        error => {
          console.error('Erro ao carregar disciplina: ', error);
        }
      );
    }
  }

  get f() { return this.cursoForm.controls; }

  salvar() {
    if (this.cursoForm.invalid) {
      this.cursoForm.markAllAsTouched(); // Marca todos os campos como tocados para exibir os erros
      return;
    }

    const cursoAtualizada = this.cursoForm.getRawValue();
    this.ccursoService.updateCurso(cursoAtualizada).subscribe(
      () => {
        this.mensagemSucesso = true;
        setTimeout(() => {
          this.mensagemSucesso = false;
          this.router.navigate(['/admin/gerenciar_curso']);
        }, 3000);
      },
      error => {
        console.error('Erro ao atualizar curso: ', error);
      }
    );
  }
}

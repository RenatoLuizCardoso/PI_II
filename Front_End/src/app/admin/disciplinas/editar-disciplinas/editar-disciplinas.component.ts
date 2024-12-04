import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CdisciplinaService } from '../../../serv/admin/cdisciplina.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-editar-disciplinas',
  templateUrl: './editar-disciplinas.component.html',
  styleUrls: ['./editar-disciplinas.component.css']
})
export class EditarDisciplinasComponent implements OnInit {
  editForm!: FormGroup;
  editError: boolean = false;
  editSuccess: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private cdisciplinaService: CdisciplinaService,
    private titleService: Title
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Editar Disciplina');
    this.editForm = this.formBuilder.group({
      subjectId: [{ value: 0, disabled: true }], // Campo desativado
      subjectName: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Letras e espaços
      subjectHours: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Números apenas
    });

    this.carregarDisciplina();
  }

  get f() { return this.editForm.controls; }

  carregarDisciplina(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.cdisciplinaService.getDisciplineById(id).subscribe(
        data => {
          this.editForm.patchValue(data); // Preenche o formulário com os dados recebidos
        },
        error => {
          console.error('Erro ao carregar disciplina: ', error);
        }
      );
    }
  }

  onSubmit(): void {
    if (this.editForm.invalid) {
      this.editError = true;
      this.editForm.markAllAsTouched();
      return;
    }

    // Preparando o JSON para envio
    const updatedData = {
      subjectId: this.route.snapshot.paramMap.get('id'), // Pegando o ID da URL
      subjectName: this.editForm.value.subjectName,
      subjectHours: this.editForm.value.subjectHours
    };

    this.cdisciplinaService.updateDiscipline(updatedData).subscribe(
      response => {
        console.log('Disciplina atualizada com sucesso', response);
        this.editSuccess = true;
        this.editError = false;
        setTimeout(() => {
          this.router.navigate(['/admin/gerenciar_disc']); // Redireciona após sucesso
        }, 3000);
      },
      error => {
        console.error('Erro ao atualizar disciplina: ', error);
        this.editError = true;
      }
    );
  }
}

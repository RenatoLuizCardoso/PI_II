import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CcursoService } from '../../../serv/admin/ccurso.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastrar-curso',
  templateUrl: './cadastrar-curso.component.html',
  styleUrls: ['./cadastrar-curso.component.css']
})
export class CadastrarCursoComponent implements OnInit {
  registerForm!: FormGroup;
  registerError: boolean = false;
  registerSuccess: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private ccursoService: CcursoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      courseName: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Letras e espaços
      courseSemester: ['', [Validators.required]],
      coursePeriod: ['', Validators.required],
      courseSubjects: ['', [Validators.required, Validators.pattern('^[0-9]+$')]] // Aceita números
    });
  }

  get f() { return this.registerForm.controls; }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerError = true;
      return;
    }

    // Transformando os dados do formulário para o formato desejado
    const courseData = {
      courseName: this.registerForm.value.courseName,  // Nome do curso
      courseSemester: this.registerForm.value.courseSemester,  // Semestre
      coursePeriod: this.registerForm.value.coursePeriod,  // Período
      courseSubjects: [parseInt(this.registerForm.value.courseSubjects)]  // Converte para array de números
    };

    // Log dos dados que estão sendo enviados (para debug)
    console.log('Dados enviados para o servidor:', JSON.stringify(courseData, null, 2));

    // Envia os dados com o token via HTTP
    this.ccursoService.registerCurso(courseData).subscribe(
      response => {
        console.log('Formulário enviado com sucesso', response);
        this.registerSuccess = true;
        this.registerForm.reset();
        this.registerError = false;
        setTimeout(() => this.registerSuccess = false, 5000);
        this.router.navigate(['/admin/gerenciar_curso']);
      },
      error => {
        console.error('Erro ao enviar o formulário', error);
        this.registerError = true;
      }
    );
  }

  onReset(): void {
    this.registerForm.reset();
    this.registerError = false;
    this.registerSuccess = false;
  }
}

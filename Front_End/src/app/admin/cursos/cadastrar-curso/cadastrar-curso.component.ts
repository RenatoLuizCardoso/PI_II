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
  disciplines: any[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private ccursoService: CcursoService,
    private router: Router
   

  ) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      nameCourse: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Letras e espaços
      semester: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Números apenas
      period: ['', Validators.required],
      discipline: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Letras e espaços
    });
    this.loadDisciplines()
  }
  private loadDisciplines(): void {
    this.ccursoService.getDisciplines().subscribe(
      data => {
        this.disciplines = data || []; // Verifique se a estrutura do JSON está correta
      },
      error => {
        console.error('Erro ao buscar professores', error);
      }
    );
  }

  get f() { return this.registerForm.controls; }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerError = true;
      return;
    }

    this.ccursoService.registerCurso(this.registerForm.value).subscribe(
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

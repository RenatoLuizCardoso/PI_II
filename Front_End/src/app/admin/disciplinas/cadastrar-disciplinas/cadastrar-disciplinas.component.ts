import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CdisciplinaService } from '../../../serv/admin/cdisciplina.service';

@Component({
  selector: 'app-cadastrar-disciplinas',
  templateUrl: './cadastrar-disciplinas.component.html',
  styleUrls: ['./cadastrar-disciplinas.component.css']
})
export class CadastrarDisciplinasComponent implements OnInit {
  registerForm!: FormGroup;
  registerError: boolean = false;
  registerSuccess: boolean = false;
  professores: any[] = [];

  constructor(private formBuilder: FormBuilder, private cdisciplinaService: CdisciplinaService) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      disciplineName: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Letras e espaços
      description: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Letras e espaços
      hour: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Números apenas
     
      objective: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Letras e espaços
      syllabus: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]] // Letras e espaços
    });

    
  }

  
  get f() { return this.registerForm.controls; }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerError = true;
      this.registerForm.markAllAsTouched(); // Marca todos os campos como tocados para exibir os erros
      return;
    }

    this.cdisciplinaService.registerDisciplines(this.registerForm.value).subscribe(
      response => {
        console.log('Formulário enviado com sucesso', response);
        this.registerSuccess = true;
        this.registerForm.reset();
        this.registerError = false;
        setTimeout(() => this.registerSuccess = false, 5000);
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

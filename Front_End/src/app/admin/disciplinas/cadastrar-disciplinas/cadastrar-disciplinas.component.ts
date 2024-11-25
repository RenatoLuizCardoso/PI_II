import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CdisciplinaService } from '../../../serv/admin/cdisciplina.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-cadastrar-disciplinas',
  templateUrl: './cadastrar-disciplinas.component.html',
  styleUrls: ['./cadastrar-disciplinas.component.css']
})
export class CadastrarDisciplinasComponent implements OnInit {
  registerForm!: FormGroup;
  registerError: boolean = false;
  registerSuccess: boolean = false;

  constructor(private formBuilder: FormBuilder, private cdisciplinaService: CdisciplinaService, private titleService: Title) {}

  ngOnInit(): void {
    this.titleService.setTitle('Cadastro de Disciplinas');
    this.registerForm = this.formBuilder.group({
      subjectName: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Letras e espaços
      subjectHours: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Números apenas
    });
  }

  get f() { return this.registerForm.controls; }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerError = true;
      this.registerForm.markAllAsTouched(); // Marca todos os campos como tocados para exibir os erros
      return;
    }

    // Preparando o JSON para enviar com os dados corretos
    const formData = {
      subjectId: 0, // ID fixo conforme sua solicitação
      subjectName: this.registerForm.value.subjectName, // Nome da disciplina
      subjectHours: this.registerForm.value.subjectHours // Carga horária
    };

    // Chamando o serviço para registrar a disciplina
    this.cdisciplinaService.registerDisciplines(formData).subscribe(
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

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProfessoresService } from '../../../serv/admin/professores.service'; // Importar o serviço correto

@Component({
  selector: 'app-cadastrar-prof',
  templateUrl: './cadastrar-prof.component.html',
  styleUrls: ['./cadastrar-prof.component.css']
})
export class CadastrarProfComponent implements OnInit {
  registerForm!: FormGroup;
  registerError: boolean = false;
  registerSuccess: boolean = false;
  passwdGerada!: string; // Variável para armazenar a passwd gerada
  cursos: any[] = [];

  constructor(private formBuilder: FormBuilder, private professoresService: ProfessoresService) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Nome: letras e espaços apenas
      curso: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Letras e espaços
      emailI: ['', [Validators.required, Validators.email]], // Email institucional: obrigatório e formato válido
      passwd: [''],
      emailP: ['', [Validators.email]], // Email pessoal: formato válido (opcional)
      tel: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Telefone: números apenas
    });
    this.loadCursos();
  }

  private loadCursos(): void {
    this.professoresService.getCursos().subscribe(
      data => {
        this.cursos = data || []; // Verifique se a estrutura do JSON está correta
      },
      error => {
        console.error('Erro ao buscar professores', error);
      }
    );
  }

  get f() { return this.registerForm.controls; }

  // Função para gerar a passwd aleatória
  gerarpasswdAleatoria(length: number = 8): string {
    const caracteres = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+[]{}|;:,.<>?';
    let passwd = '';
    for (let i = 0; i < length; i++) {
      const randomIndex = Math.floor(Math.random() * caracteres.length);
      passwd += caracteres[randomIndex];
    }
    return passwd;
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.registerError = true;
      this.registerForm.markAllAsTouched(); // Marca todos os campos como tocados para exibir os erros
      return;
    }

    // Gerar passwd aleatória
    const passwdAleatoria = this.gerarpasswdAleatoria();
    this.passwdGerada = passwdAleatoria; // Armazena a passwd gerada (opcional, para exibição)

    // Atualizar o campo de passwd no formulário com a passwd gerada
    this.registerForm.patchValue({ passwd: passwdAleatoria });

    // Obter os dados do formulário, incluindo a passwd gerada
    const dadosFormulario = this.registerForm.value;

    // Enviar o JSON com os dados, incluindo a passwd gerada
    this.professoresService.registerProfessor(dadosFormulario).subscribe(
      response => {
        console.log('Form submitted successfully', response);
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

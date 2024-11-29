import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProfessoresService } from '../../../serv/admin/professores.service'; // Serviço de cadastro de professor
import { CdisciplinaService } from '../../../serv/admin/cdisciplina.service'; // Serviço de disciplinas
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-cadastrar-prof',
  templateUrl: './cadastrar-prof.component.html',
  styleUrls: ['./cadastrar-prof.component.css']
})
export class CadastrarProfComponent implements OnInit {
  registerForm!: FormGroup;
  registerError: boolean = false;
  registerSuccess: boolean = false;
  disciplinas: any[] = []; // Lista de disciplinas carregadas
  senhaGerada!: string; // Variável para armazenar a senha gerada

  constructor(
    private formBuilder: FormBuilder,
    private professoresService: ProfessoresService,
    private cdisciplinaService: CdisciplinaService,
    private titleService: Title
  ) {}

  ngOnInit(): void {
    this.carregarDisciplinas();
    this.titleService.setTitle('Cadastro de Professor');

    this.registerForm = this.formBuilder.group({
      teacherName: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Nome
      teacherArea: ['', [Validators.required]], // Área de ensino
      researchLine: ['', [Validators.required]], // Linha de pesquisa
      institutionalEmail: ['', [Validators.required, Validators.email]], // Email institucional
      personalEmail: ['', [Validators.email]], // Email pessoal (opcional)
      personalPhone: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Telefone pessoal
      businessPhone: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Telefone corporativo
      teacherSubjects: [null, [Validators.required]], // Agora é um único valor (não um array)
      teacherPassword: ['', [Validators.required]] // Campo de senha para ser enviado ao backend
    });
  }

  // Função para carregar as disciplinas
  carregarDisciplinas() {
    this.cdisciplinaService.getDisciplines().subscribe(
      (data) => {
        this.disciplinas = data;
      },
      (error) => {
        console.error('Erro ao carregar disciplinas:', error);
      }
    );
  }

  // Função para acessar os controles do formulário
  get f() { return this.registerForm.controls; }

  // Função para gerar senha aleatória
  gerarSenhaAleatoria(length: number = 8): string {
    const caracteres = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+[]{}|;:,.<>?';
    let senha = '';
    for (let i = 0; i < length; i++) {
      const randomIndex = Math.floor(Math.random() * caracteres.length);
      senha += caracteres[randomIndex];
    }
    return senha;
  }

  // Função chamada quando o formulário é submetido
  onSubmit(): void {
    console.log('Formulário enviado', this.registerForm.value);

    // Gerar senha aleatória
    this.senhaGerada = this.gerarSenhaAleatoria();
    console.log('Senha gerada:', this.senhaGerada);

    // Atualizar o campo de senha no formulário para que fique visível
    this.registerForm.patchValue({ teacherPassword: this.senhaGerada });
    console.log('Dados do formulário após atualização da senha:', this.registerForm.value);

    // Verificando se o formulário é válido antes de prosseguir
    if (this.registerForm.invalid) {
      this.registerError = true;
      this.registerForm.markAllAsTouched();
      console.log('Formulário inválido:', this.registerForm.errors);
      return;
    }

    // Enviar os dados para o serviço
    const dadosFormulario = this.registerForm.value;
    dadosFormulario.teacherSubjects = Array.isArray(dadosFormulario.teacherSubjects)
      ? dadosFormulario.teacherSubjects.map(Number)
      : [Number(dadosFormulario.teacherSubjects)];

    this.professoresService.registerProfessor(dadosFormulario).subscribe(
      response => {
        console.log('Professor registrado com sucesso!', response);
        this.registerSuccess = true;
        this.registerForm.reset();
        this.registerError = false;
        setTimeout(() => this.registerSuccess = false, 5000);
      },
      error => {
        console.error('Erro ao registrar professor:', error);
        this.registerError = true;
      }
    );
  }

  // Função para resetar o formulário
  onReset(): void {
    this.registerForm.reset();
    this.senhaGerada = ''; // Limpar a senha gerada
  }
}

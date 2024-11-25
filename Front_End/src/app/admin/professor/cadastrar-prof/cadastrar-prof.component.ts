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
  passwdGerada!: string; // Variável para armazenar a senha gerada

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
      teacherPassword: ['', [Validators.required]], // Senha (pode ser gerada automaticamente)
      teacherSubjects: [null, [Validators.required]] // Agora é um único valor (não um array)
    });
  }

  // Função para carregar as disciplinas da API
  carregarDisciplinas() {
    this.cdisciplinaService.getDisciplines().subscribe(
      (data) => {
        console.log('Disciplinas carregadas:', data);  // Verifique aqui se as disciplinas estão vindo corretamente
        this.disciplinas = data; // Armazena as disciplinas carregadas
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
    console.log('Formulário enviado', this.registerForm.value); // Logando os dados do formulário antes de qualquer validação

    // Gerar senha aleatória
    const senhaGerada = this.gerarSenhaAleatoria();
    console.log('Senha gerada:', senhaGerada); // Logando a senha gerada

    // Atualizar o campo de senha no formulário
    this.registerForm.patchValue({ teacherPassword: senhaGerada });
    console.log('Dados do formulário após atualização da senha:', this.registerForm.value); // Logando os dados do formulário após atualização

    // Verificando se o formulário está válido
    if (this.registerForm.invalid) {
      this.registerError = true;
      this.registerForm.markAllAsTouched(); // Marca todos os campos como tocados
      console.log('Formulário inválido:', this.registerForm.errors); // Logando erros de formulário
      return;
    }

    // Obter os dados do formulário, incluindo a senha gerada
    const dadosFormulario = this.registerForm.value;

    // Garantir que teacherSubjects seja um array
    if (!Array.isArray(dadosFormulario.teacherSubjects)) {
      // Se não for um array (caso de uma única disciplina), converte em um array
      dadosFormulario.teacherSubjects = [Number(dadosFormulario.teacherSubjects)];
    } else {
      // Caso já seja um array, converte os IDs de disciplinas para números
      dadosFormulario.teacherSubjects = dadosFormulario.teacherSubjects.map((subjectId: any) => Number(subjectId));
    }

    console.log('Dados do formulário para envio:', dadosFormulario); // Logando os dados a serem enviados para a API

    // Enviar os dados para o serviço
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
        if (error.status === 400) {
          console.error('Erro 400: Dados inválidos ou incompletos.');
        } else if (error.status === 401) {
          console.error('Erro 401: Não autorizado (Token inválido ou expirada).');
        } else if (error.status === 500) {
          console.error('Erro 500: Erro interno do servidor.');
        } else {
          console.error(`Erro desconhecido: ${error.status} - ${error.message}`);
        }
      }
    );
  }

  // Função para resetar o formulário
  onReset(): void {
    this.registerForm.reset();
  }
}

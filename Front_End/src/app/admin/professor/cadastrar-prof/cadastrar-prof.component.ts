import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
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
  disciplinasFiltradas: any[] = []; // Lista filtrada de disciplinas
  selectedDisciplines: number[] = []; // Array para armazenar as disciplinas selecionadas
  disciplineSelectionOpen: boolean = false; // Controle de visibilidade do modal de disciplinas
  pesquisaControl: FormControl = new FormControl(''); // Controle de formulário para o campo de pesquisa

  constructor(
    private formBuilder: FormBuilder,
    private professoresService: ProfessoresService,
    private cdisciplinaService: CdisciplinaService,
    private titleService: Title
  ) {}

  ngOnInit(): void {
    this.carregarDisciplinas();
    this.titleService.setTitle('Cadastro de Professor');

    // Inicializando o formulário
    this.registerForm = this.formBuilder.group({
      teacherName: ['', [Validators.required, Validators.pattern('^[A-Za-zÀ-ÿ\\s]+$')]], // Nome
      teacherArea: ['', [Validators.required]], // Área de ensino
      researchLine: ['', [Validators.required]], // Linha de pesquisa
      institutionalEmail: ['', [Validators.required, Validators.email]], // Email institucional
      personalEmail: ['', [Validators.email]], // Email pessoal (opcional)
      personalPhone: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Telefone pessoal
      businessPhone: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Telefone corporativo
      teacherSubjects: [[], [Validators.required]], // Agora é um array (não um único valor)
      teacherPassword: ['', [Validators.required]] // Campo de senha para ser enviado ao backend
    });

    // Reagindo às mudanças do campo de pesquisa
    this.pesquisaControl.valueChanges.subscribe(value => {
      this.filtrarDisciplinas();
    });
  }

  // Função para carregar as disciplinas
  carregarDisciplinas() {
    this.cdisciplinaService.getDisciplines().subscribe(
      (data) => {
        this.disciplinas = data;
        this.disciplinasFiltradas = [...this.disciplinas]; // Inicializa a lista filtrada com todas as disciplinas
      },
      (error) => {
        console.error('Erro ao carregar disciplinas:', error);
      }
    );
  }

  // Função chamada quando o formulário é submetido
  onSubmit(): void {
    console.log('Formulário enviado', this.registerForm.value);

    // Verificando se o formulário é válido antes de prosseguir
    if (this.registerForm.invalid) {
      this.registerError = true;
      this.registerForm.markAllAsTouched();
      console.log('Formulário inválido:', this.registerForm.errors);
      return;
    }

    // Transforma teacherSubjects em um array de objetos com a propriedade subjectId
    const dadosFormulario = this.registerForm.value;

    if (Array.isArray(dadosFormulario.teacherSubjects)) {
      // Aqui estamos dizendo que o subjectId é do tipo 'number'
      dadosFormulario.teacherSubjects = dadosFormulario.teacherSubjects.map((subjectId: number) => ({ subjectId }));
    }

    console.log('Dados do formulário após correção do campo teacherSubjects:', dadosFormulario);

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
      }
    );
  }

  // Função para resetar o formulário
  onReset(): void {
    this.registerForm.reset();
    this.selectedDisciplines = []; // Limpar as disciplinas selecionadas
  }

  // Função para abrir e fechar o modal de seleção de disciplinas
  toggleDisciplineSelection() {
    this.disciplineSelectionOpen = !this.disciplineSelectionOpen;
  }

  // Função chamada quando uma disciplina é selecionada ou desmarcada
  onDisciplineChange(event: any): void {
    const disciplinaId = event.target.value;
    const index = this.selectedDisciplines.indexOf(disciplinaId);

    // Se a disciplina já foi selecionada, removemos do array, caso contrário, adicionamos
    if (index === -1) {
      this.selectedDisciplines.push(disciplinaId);
    } else {
      this.selectedDisciplines.splice(index, 1);
    }

    console.log('Disciplinas selecionadas: ', this.selectedDisciplines);
  }

  // Função chamada quando o botão "Confirmar" do modal de disciplinas é pressionado
  confirmDisciplineSelection(): void {
    // Atualizando o valor do FormControl teacherSubjects
    this.registerForm.patchValue({
      teacherSubjects: [...this.selectedDisciplines]  // Atualizando com as disciplinas selecionadas
    });

    console.log('Disciplinas confirmadas: ', this.selectedDisciplines);

    // Fechar o modal
    this.toggleDisciplineSelection();
  }

  // Função para filtrar as disciplinas com base no texto digitado no campo de pesquisa
  filtrarDisciplinas() {
    if (this.pesquisaControl.value) {
      this.disciplinasFiltradas = this.disciplinas.filter(disciplina => 
        disciplina.subjectName.toLowerCase().includes(this.pesquisaControl.value.toLowerCase())
      );
    } else {
      this.disciplinasFiltradas = [...this.disciplinas];
    }
  }

  // Função para acessar os controles do formulário
  get f() { return this.registerForm.controls; }
}

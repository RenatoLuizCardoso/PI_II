import { Component, OnInit } from '@angular/core';
import { PerfilService } from '../../serv/prof/perfil.service';  // Importe o serviço
import { Router } from '@angular/router';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {
  showDropdown = false;
  emailErrorMessage: string = '';
  selectedImage: string | ArrayBuffer | null = null;
  subjectMap: { [key: string]: string } = {};  // Mapeamento otimizado de subjects

  // Dados do professor
  professorData = {
    teacherId: null,
    teacherName: '',
    institutionalEmail: '',
    personalEmail: '',
    personalPhone: '',
    businessPhone: '',
    researchLine: '',
    teacherArea: '',
    teacherSubjects: []  // Lista de subjectIds
  };

  constructor(
    private perfilService: PerfilService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadProfile();    // Carregar os dados do perfil
    this.loadSubjects();   // Carregar o mapeamento de subjects
  }

  // Função para carregar os dados do perfil
  loadProfile() {
    this.perfilService.getProfile().subscribe({
      next: (data) => {
        this.professorData = data;  // Preenche os dados do professor
      },
      error: (err) => {
        console.error('Erro ao carregar o perfil', err);
        this.router.navigate(['/login']);  // Redireciona para login em caso de erro
      }
    });
  }

  // Função para carregar o mapa de subjects
  loadSubjects() {
    this.perfilService.getSubjectsMap().subscribe({
      next: (subjectMap) => {
        this.subjectMap = subjectMap;  // Armazenar o mapa de subjects
      },
      error: (err) => {
        console.error('Erro ao carregar os subjects', err);
      }
    });
  }

  // Função para mapear os subjectIds para os subjectNames
  getSubjectNames(): string {
    return this.professorData.teacherSubjects
      .map(subjectId => this.subjectMap[subjectId] || 'Assunto não encontrado')  // Obtém o nome do subject usando o id
      .join(', ');  // Junte os nomes dos subjects separados por vírgula
  }

  // Função para abrir o seletor de arquivos
  triggerFileInput() {
    const fileInput = document.getElementById('fileInput') as HTMLInputElement;
    fileInput.click();
  }

  // Função para processar a imagem selecionada
  onImageSelected(event: Event) {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files[0]) {
      const file = input.files[0];
      const reader = new FileReader();

      reader.onload = () => {
        this.selectedImage = reader.result;  // A imagem selecionada é carregada como uma string base64
      };

      reader.readAsDataURL(file); // Converte a imagem em base64 para exibição
    }
  }

  // Função para alternar a exibição do dropdown para editar o email
  toggleEmailDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  // Função para validar o formato do email
  isValidEmail(email: string): boolean {
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return emailPattern.test(email);
  }

  // Função para registrar um novo email
  registerNewEmail() {
    const newEmailInput = document.getElementById('newEmail') as HTMLInputElement;
    const newEmailValue = newEmailInput.value;

    if (this.isValidEmail(newEmailValue)) {
      this.professorData.personalEmail = newEmailValue;
      this.showDropdown = false;
      this.emailErrorMessage = '';
    } else {
      this.emailErrorMessage = 'Por favor, insira um email válido.';
    }
  }
}

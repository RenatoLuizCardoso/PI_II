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
    teacherSubjects: []
  };

  constructor(
    private perfilService: PerfilService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadProfile();
  }

  // Função para carregar os dados do perfil
  loadProfile() {
    this.perfilService.getProfile().subscribe({
      next: (data) => {
        // Preenche os campos com os dados recebidos
        this.professorData.teacherId = data.teacherId || null;
        this.professorData.teacherName = data.teacherName || 'Nome não encontrado';
        this.professorData.institutionalEmail = data.institutionalEmail || 'Email institucional não encontrado';
        this.professorData.personalEmail = data.personalEmail || 'Email pessoal não encontrado';
        this.professorData.personalPhone = data.personalPhone || 'Telefone pessoal não encontrado';
        this.professorData.businessPhone = data.businessPhone || 'Telefone comercial não encontrado';
        this.professorData.researchLine = data.researchLine || 'Linha de pesquisa não encontrada';
        this.professorData.teacherArea = data.teacherArea || 'Área de atuação não encontrada';
        this.professorData.teacherSubjects = data.teacherSubjects || [];
      },
      error: (err) => {
        console.error('Erro ao carregar o perfil', err);
        this.router.navigate(['/login']); // Redireciona para o login se falhar ao carregar os dados
      }
    });
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
        this.selectedImage = reader.result; // A imagem selecionada é carregada como uma string base64
      };

      reader.readAsDataURL(file); // Converte a imagem em base64 para exibição
    }
  }

  // Outras funções existentes
  toggleEmailDropdown() {
    this.showDropdown = !this.showDropdown;
  }

  isValidEmail(email: string): boolean {
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return emailPattern.test(email);
  }

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

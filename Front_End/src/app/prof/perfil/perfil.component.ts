import { Component } from '@angular/core';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent {
  showDropdown = false;
  emailErrorMessage: string = '';
  selectedImage: string | ArrayBuffer | null = null;

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
      const emailPessoalInput = document.getElementById('emailPessoal') as HTMLInputElement;
      emailPessoalInput.value = newEmailValue;
      this.showDropdown = false;
      this.emailErrorMessage = '';
    } else {
      this.emailErrorMessage = 'Por favor, insira um email válido.';
    }
  }
}

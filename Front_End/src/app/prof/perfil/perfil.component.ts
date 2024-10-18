import { Component } from '@angular/core';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent {
  
  // Valores fixos de exemplo para o perfil do professor
  professor = {
    name: 'Maria Oliveira',
    emailInstitutional: 'maria.oliveira@universidade.edu.br',
    emailPersonal: 'mariaoliveira@gmail.com',
    phone: '(21) 91234-5678'
  };

  editProfile() {
    // Função de edição simulada
    alert('Funcionalidade de edição ainda não implementada.');
  }
}
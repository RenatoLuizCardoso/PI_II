import { Component } from '@angular/core';

@Component({
  selector: 'app-tela-perfil-professor',
  templateUrl: './tela-perfil-professor.component.html',
  styleUrls: ['./tela-perfil-professor.component.css']
})
export class TelaPerfilProfessorComponent {
  professor = {
    nome: '',
    emailInstitucional: '',
    emailPessoal: '',
    telefone: '',
    fotoUrl: 'caminho/para/foto/padrao.jpg'
  };

  onFileSelected(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files && target.files[0]) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.professor.fotoUrl = e.target?.result as string;
      };
      reader.readAsDataURL(target.files[0]);
    }
  }

  salvarPerfil(): void {
    console.log('Perfil salvo:', this.professor);
  }
}

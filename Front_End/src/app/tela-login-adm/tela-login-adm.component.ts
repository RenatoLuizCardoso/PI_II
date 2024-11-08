import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthaService } from '../autha.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tela-login-adm',
  templateUrl: './tela-login-adm.component.html',
  styleUrls: ['./tela-login-adm.component.css']
})
export class TelaLoginAdmComponent {
  loginForm: FormGroup;
  loginError: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authaService: AuthaService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      adminEmail: ['', [Validators.required, Validators.email]], // Alterado para adminEmail
      adminPassword: ['', Validators.required] // Alterado para adminPassword
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { adminEmail, adminPassword } = this.loginForm.value;
      const payload = JSON.stringify({ adminEmail, adminPassword });

      console.log("Dados enviados:", payload);

      this.authaService.login(payload).subscribe({
        next: (token) => {
          console.log("Token recebido:", token);

          // Armazena o token no localStorage (ou sessionStorage) para uso posterior
          localStorage.setItem('authToken', token);

          // Redireciona para a página inicial do administrador
          this.router.navigate(['admin']);
        },
        error: (err) => {
          this.loginError = true; // Mostrar erro se a autenticação falhar
          console.error("Erro de autenticação:", err);
        }
      });
    } else {
      console.warn("Formulário inválido");
    }
  }
}

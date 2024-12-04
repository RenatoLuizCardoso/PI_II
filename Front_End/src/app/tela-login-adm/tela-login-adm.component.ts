import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthaService } from '../autha.service';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

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
    private router: Router,
    private titleService: Title
  ) {
    this.loginForm = this.fb.group({
      adminEmail: ['', [Validators.required, Validators.email]],
      adminPassword: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.titleService.setTitle('Login');
  }




  onSubmit(): void {
    if (this.loginForm.valid) {
      const { adminEmail, adminPassword } = this.loginForm.value;
      const payload = JSON.stringify({ adminEmail, adminPassword });

      console.log("Dados enviados:", payload);

      this.authaService.login(payload).subscribe({
        next: (response) => {
          if (response && response.access_token) {
            console.log("Token recebido:", response.access_token);

            // Armazenar o token no localStorage
            localStorage.setItem('adminToken', response.access_token);

            // Opcional: Armazenar outras informações, como tempo de expiração
            localStorage.setItem('expires_in', response.expires_in.toString());

            // Redirecionar para a página inicial do administrador
            this.router.navigate(['admin']);
          } else {
            // Exibe erro se o token não foi encontrado
            this.loginError = true;
            console.error("Autenticação falhou: Token não encontrado.");
          }
        },
        error: (err) => {
          this.loginError = true;
          console.error("Erro de autenticação:", err);
        }
      });
    } else {
      console.warn("Formulário inválido");
    }
  }
}

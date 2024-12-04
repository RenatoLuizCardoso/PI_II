import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthpService } from '../authp.service';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-tela-login',
  templateUrl: './tela-login.component.html',
  styleUrls: ['./tela-login.component.css']
})
export class TelaLoginComponent implements OnInit {
  loginForm!: FormGroup;
  loginError: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authpService: AuthpService,
    private router: Router,
    private titleService: Title
  ) {}

  ngOnInit(): void {
    this.titleService.setTitle('Login - Professor');
    this.loginForm = this.fb.group({
      institutionalEmail: ['', [Validators.required, Validators.email]],
      teacherPassword: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  // Função auxiliar para acessar os controles do formulário
  get f() { return this.loginForm.controls; }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { institutionalEmail, teacherPassword } = this.loginForm.value;
      const payload = JSON.stringify({ institutionalEmail, teacherPassword });

      console.log("Dados enviados:", payload);

      this.authpService.login(payload).subscribe({
        next: (response) => {
          if (response && response.access_token) {
            console.log("Token recebido:", response.access_token);

            // Armazenar o token no localStorage
            localStorage.setItem('teacherToken', response.access_token);

            // Opcional: Armazenar outras informações, como tempo de expiração
            localStorage.setItem('expires_in', response.expires_in.toString());

            // Redirecionar para a página do professor
            this.router.navigate(['professor']);
          } else {
            // Exibe erro se o token não for encontrado
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

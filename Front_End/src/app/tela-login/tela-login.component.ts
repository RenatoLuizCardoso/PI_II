import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthpService } from '../authp.service';
import { Router } from '@angular/router';
import { ProfessoresService } from '../serv/admin/professores.service';

@Component({
  selector: 'app-tela-login',
  templateUrl: './tela-login.component.html',
  styleUrl: './tela-login.component.css'
})
export class TelaLoginComponent implements OnInit {
  loginForm!: FormGroup;
  loginError: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private professoresService: ProfessoresService, // Serviço de autenticação
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      emaili: ['', [Validators.required, Validators.email]], // Validação de email institucional
      passwd: ['', [Validators.required, Validators.minLength(6)]] // Validação de passwd (mínimo 6 caracteres)
    });
  }

  // Função auxiliar para acessar os controles do formulário
  get f() { return this.loginForm.controls; }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const loginData = this.loginForm.value; // Captura os dados do formulário (emailI e passwd)

    this.professoresService.loginProfessor(loginData).subscribe(
      response => {
        // Sucesso: redirecionar o professor após login bem-sucedido
        console.log('Login realizado com sucesso', response);
        this.loginError = false;
        this.router.navigate(['/perfil']); // Redirecionar para o dashboard ou outra página
      },
      error => {
        // Falha: mostrar mensagem de erro
        console.error('Erro no login', error);
        this.loginError = true;
      }
    );
  }
}

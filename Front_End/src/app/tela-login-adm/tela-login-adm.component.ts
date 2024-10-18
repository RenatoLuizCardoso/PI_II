import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthaService } from '../autha.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-tela-login-adm',
  templateUrl: './tela-login-adm.component.html',
  styleUrl: './tela-login-adm.component.css'
})
export class TelaLoginAdmComponent {
  loginForm: FormGroup;
  loginError: boolean = false;

  constructor(private fb: FormBuilder, private authaService: AuthaService, private router: Router) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      if (this.authaService.login(username, password)) {
        this.router.navigate(['/tela-inicial-adm']); // Redirecionar para a página desejada após login
      } else {
        this.loginError = true;
      }
    }
  }
}

import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AdminAuthGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('adminToken');
    console.log('Verificando token de admin:', token);

    if (token) {
      console.log('Token de admin encontrado, permitindo acesso.');
      return true;  // Token encontrado, permite o acesso
    } else {
      console.log('Token de admin não encontrado, redirecionando para o login.');
      this.router.navigate(['/login_adm']);
      return false;  // Token não encontrado, redireciona para o login
    }
  }
}

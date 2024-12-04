// src/app/teacher-auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthpService } from './authp.service';  // Serviço de autenticação de professor

@Injectable({
  providedIn: 'root'
})
export class TeacherAuthGuard implements CanActivate {

  constructor(private authService: AuthpService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    
    // Aqui você verifica se o professor está autenticado.
    if (localStorage.getItem('teacherToken')) {
      return true;
    } else {
      // Se não estiver autenticado, redireciona para a página de login de professor
      this.router.navigate(['/login']);
      return false;
    }
  }
}

import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthpService {
  private isAuthenticated = false;
  private readonly validUsername = 'admin';
  private readonly validPassword = 'admin';

  constructor(private router: Router) {}

  login(username: string, password: string): boolean {
    if (username === this.validUsername && password === this.validPassword) {
      this.isAuthenticated = true;
      return true;
    }
    return false;
  }

  logout(): void {
    this.isAuthenticated = false;
    this.router.navigate(['/login']);
  }

  getAuthStatus(): boolean {
    return this.isAuthenticated;
  }
}

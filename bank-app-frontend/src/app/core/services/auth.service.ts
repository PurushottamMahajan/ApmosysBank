import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  login(username: string, password: string): boolean {
    if (username === 'admin' && password === 'admin') {
      localStorage.setItem('auth', 'true');
      return true;
    }
    return false;
  }

  logout(): void {
    localStorage.removeItem('auth');
  }

  isAuthenticated(): boolean {
    return localStorage.getItem('auth') === 'true';
  }
}

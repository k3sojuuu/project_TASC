import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const roles = JSON.parse(localStorage.getItem('roles') || '[]');
    if (roles.includes('USER') || roles.includes('PT')) {
      return true;
    } else {
      alert('You need to log in to access this page, please log in now');
      this.router.navigate(['/login']);
      return false;
    }
  }
}

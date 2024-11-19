import { Component } from '@angular/core';
import { faUser, faLock } from '@fortawesome/free-solid-svg-icons'; 
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'] 
})
export class LoginComponent {
  faUser = faUser;
  faLock = faLock;

  username: string = '';
  password: string = '';

  constructor(private router: Router) {}

  onLogin() {
    if (this.username === 'admin12345' && this.password === '12345') {
      this.router.navigate(['/dashboard']);
    } else {
      alert('Sai tài khoản hoặc mật khẩu!');
    }
  }
}



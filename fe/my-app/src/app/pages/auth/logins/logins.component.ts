import { Component } from '@angular/core';
import { faUser, faLock } from '@fortawesome/free-solid-svg-icons';
import { AuthServicesService } from '../../../services/auth-services.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-logins',
  templateUrl: './logins.component.html',
  styleUrl: './logins.component.css'
})

export class LoginsComponent {
  faUser = faUser;
  faLock = faLock;



  username: string ='';
  password: string ='';
  usernameError: string ='';
  passwordError: string ='';
  isLoggedIn: boolean = false;

  constructor(private authService: AuthServicesService,private router:Router){}

  onSubmit(){
    this.authService.login(this.username, this.password).subscribe({
      next: (data) => {
        if(data && data.body && data.body.token){
          console.log('Login successfull',data);
          this.isLoggedIn = true;
          localStorage.setItem('token', data.body.token);
          localStorage.setItem('isLoggedIn', 'true');
          localStorage.setItem('userId', data.body.id)
          const roles = data.body.roles || [];
          const roleAuthorities = roles.map((role: any) => role.authority);
          localStorage.setItem('roles', JSON.stringify(roleAuthorities));
          this.router.navigate(['']);

      }
    },
      error: (err) => {
          if(err.error === 'username not found'){
            this.usernameError ='Username not found';
            this.passwordError ='';
          }else if(err.error ==='Invalid password'){
            this.passwordError = 'Invalid password';
            this.usernameError = '';
          }else{
            this.usernameError ='';
            this.passwordError = 'An error occurred. Please try again later.';
          }
          console.error('Login error', err);
      }
    })
  }
}

import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { using } from 'rxjs';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent {
      formForgotPassword: any = {
        username: '',
        email: ''
      };

      constructor(private http: HttpClient,private router:Router){}

      onSubmit(){
        const url = 'http://localhost:8000/api/sv1/auth/create_code_forgot_password';

        this.http.post(url, this.formForgotPassword).subscribe(
          response =>{
            console.log('Code sent successfully',response);
            alert('The code has been sent to your email, please check')
            this.router.navigate(['/confirm-code']);
          },
          error => {
            console.error('Error sending code',error);
          }
        );
      }
}

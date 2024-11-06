import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import { AuthServicesService } from '../../../services/auth-services.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-confirm-password',
  templateUrl: './confirm-password.component.html',
  styleUrl: './confirm-password.component.css'
})
export class ConfirmPasswordComponent {

  codeConfirm: string = '';
  errorMessage: string = '';

  constructor(private authService:AuthServicesService,private router:Router){}


  onSubmitCode() {
    if (this.codeConfirm.length === 6) {
      this.authService.claimPassword(this.codeConfirm).subscribe({
        next: (res) =>{
          console.log("Success")
          alert('Password claim successful!')
          this.router.navigate(['/login'])
        },
        error: (err:HttpErrorResponse)=>{
          console.error('Error claiming password:', err);
          this.errorMessage = 'Failed to claim password. Please try again.';
        }
      });
    } else {
      alert('Please enter a 6-digit code');
    }
  }
}

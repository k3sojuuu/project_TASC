
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { debounceTime, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{
 registerForm: FormGroup;
 usernameExists: boolean = false;
 emailExists: boolean = false;
 apiUrl: string = 'http://localhost:8000/api/sv1/auth/register';

 constructor(
  private fb: FormBuilder,
  private http: HttpClient,
  private router: Router
){
  this.registerForm = this.fb.group({
    first_name: ['', Validators.required],
    last_name: ['', Validators.required],
    username: ['', [Validators.required, Validators.minLength(4)]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    email: ['', [Validators.required, Validators.email]],
    phone_number: ['', [Validators.required, Validators.pattern(/^[0-9]{10,11}$/)]],
    address: ['', Validators.required],
    age: ['', [Validators.required, Validators.min(1)]],
    gender: ['', Validators.required]
  });
}
isActive(){

}


ngOnInit(): void {
  this.registerForm.get('username')?.valueChanges.pipe(
    debounceTime(5000),
    switchMap(username => this.checkUsernameExists(username))
  ).subscribe(exists => {
    this.usernameExists = exists;
  });

  this.registerForm.get('email')?.valueChanges.pipe(
    debounceTime(5000),
    switchMap(email => this.checkEmailExists(email))
  ).subscribe(exists => {
    this.emailExists = exists;
  });
}

checkUsernameExists(username: string) {
  return this.http.get<boolean>(`http://localhost:8000/api/sv1/auth/check_username?username=${username}`);
}



checkEmailExists(email: string) {
  return this.http.get<boolean>(`http://localhost:8000/api/sv1/auth/check_email?email=${email}`);
}

onSubmit(){
  if(this.registerForm.valid && !this.usernameExists && !this.emailExists){
    this.http.post(this.apiUrl,this.registerForm.value).subscribe({
      next: (response) => {
        console.log('Resgis ss:',response);
        this.router.navigate(['/login'])
      },
      error: (error) => {
        console.error('Registration error:', error);
      }
    });
  }else{
    if (this.usernameExists) {
      alert("Username already exists.");
    }
    if (this.emailExists) {
      alert("Email already exists.");
    }
}
}
}

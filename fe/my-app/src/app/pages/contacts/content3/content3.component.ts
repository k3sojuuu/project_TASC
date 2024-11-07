import { Component, OnInit } from '@angular/core';
import * as AOS from 'aos';

@Component({
  selector: 'app-content3',
  templateUrl: './content3.component.html',
  styleUrl: './content3.component.css'
})
export class Content3Component implements OnInit {
  username: string = '';
  password: string = '';


  ngOnInit(): void {
      AOS.init({
        duration: 2000,
        easing: 'ease-in-out',
        once: false
      })

      window.addEventListener('scroll', () => {
        AOS.refresh();
      });
  }

  condition: boolean = false;

  add(){
    this.condition = !this.condition;
  }

  onSubmit(){
    this.username
    this.password
    console.log("Username: " + this.username + ",Password:" + this.password)
  }

 isValid: boolean = false
  formData(){
    if(this.username && this.password){
       this.isValid = true
    }else{
      this.isValid = false
    }
  }
}

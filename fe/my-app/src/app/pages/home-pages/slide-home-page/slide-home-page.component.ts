import { Component, OnInit } from '@angular/core';
import * as AOS from 'aos';

@Component({
  selector: 'app-slide-home-page',
  templateUrl: './slide-home-page.component.html',
  styleUrl: './slide-home-page.component.css'
})
export class SlideHomePageComponent implements OnInit{
ngOnInit(): void {
  AOS.init({
    duration: 200,
    easing: 'ease-in-out',
    once: true
  })
}
slideShow: number = 0;

prevSlide(){
  this.slideShow = (this.slideShow - 1 + 3) % 3;
}

nextSlide(): void {
  this.slideShow = (this.slideShow + 1) % 3;
}

}

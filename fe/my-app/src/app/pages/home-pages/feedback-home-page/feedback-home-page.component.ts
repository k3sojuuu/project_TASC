import { Component } from '@angular/core';
import * as AOS from 'aos';

@Component({
  selector: 'app-feedback-home-page',
  templateUrl: './feedback-home-page.component.html',
  styleUrl: './feedback-home-page.component.css'
})
export class FeedbackHomePageComponent {
  currentSlide: number = 0;

  ngOnInit(): void {
    setInterval(() => {
      this.nextSlide();
    }, 5000);

    AOS.init({
      duration: 1000,
      easing: 'ease-in-out',
      once: true
    })
  }

  nextSlide(): void {
    this.currentSlide = (this.currentSlide + 1) % 3;
  }

  prevSlide(): void {
    this.currentSlide = (this.currentSlide - 1 + 3) % 3;
  }

}

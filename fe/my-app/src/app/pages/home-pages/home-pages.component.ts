import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-pages',
  templateUrl: './home-pages.component.html',
  styleUrl: './home-pages.component.css'
})
export class HomePagesComponent implements OnInit{
  slides: { imageUrl: string, caption: string }[] = [
    { imageUrl: '"assets/img/img.jpg"', caption: 'Đây là nội dung Slide 1.' },
    { imageUrl: '"assets/img/img.jpg"', caption: 'Đây là nội dung Slide 2.' },
    { imageUrl: '"assets/img/img.jpg"', caption: 'Đây là nội dung Slide 3.' }
  ];

  currentSlideIndex: number = 0;

  ngOnInit() {
    this.showSlides();
  }

  showSlides() {
    const slides = document.getElementsByClassName("mySlides");

    if (slides.length === 0) return;

    for (let i = 0; i < slides.length; i++) {
        slides[i].setAttribute('style', 'display: none;');
    }
    this.currentSlideIndex++;
    if (this.currentSlideIndex > slides.length) { this.currentSlideIndex = 1; }
    slides[this.currentSlideIndex - 1].setAttribute('style', 'display: block;');
    setTimeout(() => this.showSlides(), 3000); // Chuyển slide mỗi 3 giây
}

  plusSlides(n: number) {
    this.currentSlideIndex += n;
    if (this.currentSlideIndex > this.slides.length) { this.currentSlideIndex = 1; }
    if (this.currentSlideIndex < 1) { this.currentSlideIndex = this.slides.length; }
    this.showSlides();
  }
}

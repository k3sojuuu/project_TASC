import { Component, OnInit } from '@angular/core';
import { Swiper } from 'swiper/types';
import { ViewChild, AfterViewInit, ElementRef } from '@angular/core';
import * as AOS from 'aos';

@Component({
  selector: 'app-content1',
  templateUrl: './content1.component.html',
  styleUrl: './content1.component.css'
})
export class Content1Component implements OnInit{

  ngOnInit(): void {
    AOS.init({
      duration: 1000,
      easing: 'ease-in-out',
      once: true
    })
    window.addEventListener('scroll', () => {
      AOS.refresh();
    });
  }
  // title: string = 'Video Background Title';
  // description: string = 'This is a description for the video background.';
  @ViewChild('video', { static: false }) video!: ElementRef<HTMLVideoElement>;

  ngAfterViewInit(): void {
    if (this.video && this.video.nativeElement) {
      this.video.nativeElement.muted = true;
    }
  }
}

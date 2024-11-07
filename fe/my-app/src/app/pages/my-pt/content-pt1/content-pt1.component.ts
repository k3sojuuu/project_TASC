import { Component, OnInit } from '@angular/core';
import * as AOS from 'aos';
@Component({
  selector: 'app-content-pt1',
  templateUrl: './content-pt1.component.html',
  styleUrl: './content-pt1.component.css'
})
export class ContentPt1Component implements OnInit{
  ngOnInit(): void {
    AOS.init({
      duration: 500,
      easing: 'ease-in-out',
      once: true
    })
  }


}

import { Component } from '@angular/core';
import { last } from 'rxjs';

@Component({
  selector: 'app-content-pt2',
  templateUrl: './content-pt2.component.html',
  styleUrl: './content-pt2.component.css'
})
export class ContentPt2Component {
  backgrounds: string[] = [
    'url("assets/img/PtPage/ptpage1.png")',
    'url("assets/img/PtPage/ptpage2.png")',
    'url("assets/img/PtPage/ptpage3.png")'
  ];
  backgroundSelector: string = this.backgrounds[0];

  hideButtonGym: boolean = false;
  hideButtonPilates: boolean = false;
  hideButtonYoga: boolean = false;

  leaveGym(){
    this.hideButtonGym = false;
  }
  leavePilates(){
    this.hideButtonPilates = false;
  }
  leaveYoga(){
    this.hideButtonYoga = false;
  }

  gymBackground() {
    this.backgroundSelector = this.backgrounds[0];
    this.hideButtonGym = true;
  }

  pilatesBackground() {
    this.backgroundSelector = this.backgrounds[1];
    this.hideButtonPilates = true;
  }

  yogaBackground() {
    this.backgroundSelector = this.backgrounds[2];
    this.hideButtonYoga = true;
  }


}

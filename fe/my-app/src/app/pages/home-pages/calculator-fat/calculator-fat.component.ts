import { Component } from '@angular/core';

@Component({
  selector: 'app-calculator-fat',
  templateUrl: './calculator-fat.component.html',
  styleUrl: './calculator-fat.component.css'
})
export class CalculatorFatComponent {
    waist: number = 0;
    neck: number = 0;
    height: number = 0;
    hip: number = 0;
    age: number = 0;
    gender: string = 'male';
    bfp: number | null = null;

    calculateBFP(){
      if (this.gender === 'male'){
        this.bfp = (86.010 * Math.log10(this.waist - this.neck)) - (70.041 * Math.log10(this.height)) + 36.76;
      } else {
        this.bfp = (163.205 * Math.log10(this.waist + this.hip - this.neck)) - (97.684 * Math.log10(this.height)) - 78.387;
      }
    }
}

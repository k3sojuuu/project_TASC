import { NgModule,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SlideHomePageComponent } from './slide-home-page/slide-home-page.component';
import { Content1Component } from './content1/content1.component';
import { Content2Component } from './content2/content2.component';
import { CalculatorFatComponent } from './calculator-fat/calculator-fat.component';
import { Content3Component } from './content3/content3.component';
import { FeedbackHomePageComponent } from './feedback-home-page/feedback-home-page.component';




@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class HomePagesModule { }

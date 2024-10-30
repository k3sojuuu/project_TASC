import { NgModule,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SlideHomePageComponent } from './slide-home-page/slide-home-page.component';
import { Content1Component } from './content1/content1.component';
import { Content2Component } from './content2/content2.component';




@NgModule({
  declarations: [
    SlideHomePageComponent,
    Content1Component,
    Content2Component
  ],
  exports:[Content1Component,Content2Component],
  imports: [
    CommonModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class HomePagesModule { }

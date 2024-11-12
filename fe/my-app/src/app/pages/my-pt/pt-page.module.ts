import { NgModule,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PayOrderComponent } from './pay-order/pay-order.component';




@NgModule({
  declarations: [

  
    PayOrderComponent
  ],
  imports: [
    CommonModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class PtPageModule { }

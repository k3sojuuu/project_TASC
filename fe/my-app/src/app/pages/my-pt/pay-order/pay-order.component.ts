import { PaymentPayOs } from './../../../model/createPaymentPayOs.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { PaymentServiceService } from '../../../services/payment-service.service';
import { ProfileServiceService } from '../../../services/profile-service.service';
import { OrderServiceService } from '../../../services/order-service.service';



@Component({
  selector: 'app-pay-order',
  templateUrl: './pay-order.component.html',
  styleUrl: './pay-order.component.css'
})
export class PayOrderComponent implements OnInit {
  selectedCourse: any;
  imgOrder: string = ''
  userId: number | null = null;
  userProfile: any | null = null;
  pagePayment: string = ''
  payosSelected: boolean = false;
  momoSelected: boolean = false;
  vnpaySelected: boolean = false;
  responseCreatePayOs: any;
  termsPopup: boolean = false;
  orderCreateResponse: number = 0

  constructor(private router: ActivatedRoute,
              private profileService:ProfileServiceService,
              private paymentService:PaymentServiceService,
              private orderService:OrderServiceService,
              private route: Router) {}

  ngOnInit() {
    if (history.state.selectedCourse) {
      this.selectedCourse = history.state.selectedCourse;
      this.imgOrder = this.selectedCourse.img
      const storedUserId = localStorage.getItem("userId");
      this.userId = storedUserId ? +storedUserId : null;
      const createOrder: any = {
        courseId: this.selectedCourse.id,
        userId: this.userId,
        quantity: 1,
        totalPrice: this.selectedCourse.price,
        ptId: this.selectedCourse.ptId
     }
     this.createOrderDetail(createOrder)
      if (this.userId !== null) {
        this.getUserByUserId(this.userId);
      }
      console.log("data from selectedCourse:",this.selectedCourse)
      console.log("img is", this.imgOrder)
    }
  }

  getUserByUserId(userId: number){
    this.profileService.getUser(userId).subscribe(
      (res : any) => {
        this.userProfile = res
        console.log("Data user is:", this.userProfile)
      },(error) => {
        console.error("Error fetching user data:", error);
      }
    )
}

CreatePaymentPayOs(paymentData:PaymentPayOs){
    this.paymentService.createPaymentPayOs(paymentData).subscribe(
      (res: any) => {
         this.responseCreatePayOs = res
         console.log("Response:", this.responseCreatePayOs.data)
         console.log("Data is:", this.responseCreatePayOs)
         this.pagePayment =   this.responseCreatePayOs.data.checkoutUrl
         console.log("payment:", this.pagePayment)
      },(error) => {
        console.error("Error fetching payment data:", error);
      }
    )
}

createOrderDetail(orderDetail: any){
  this.orderService.createOrders(orderDetail).subscribe(
    (res: any) => {
        this.orderCreateResponse = res.data
        console.log("đơn hàng tạo thành công rồi nhé,đây là log nè",this.orderCreateResponse)
        console.log("khóa chính nè:",this.orderCreateResponse)
    },(error) => {
      console.error("Error fetching order data:", error);
    }
  )
}

createPayment(){
  if(this.payosSelected){
    const paymentData: any = {
        amount: this.selectedCourse.price,
        description: 'VQWIO123',
        buyerName: this.userProfile.firstName,
        buyerEmail: this.userProfile.email,
        buyerPhone: this.userProfile.phone_number,
        buyerAddress: 'Address information here',
        orderId: this.orderCreateResponse,
        name: this.selectedCourse.nameCoarch,
        quantity: 1,
        price: this.selectedCourse.price,
        cancelUrl: '',
        returnUrl: '',
        expiredAt: 1696559798,
        signature: 'calculated-signature-value',
    }
    console.log("Data",paymentData)
    this.CreatePaymentPayOs(paymentData)
    console.log("dadada:", this.pagePayment)
    this.termsPopup = !this.termsPopup
  }else if(this.momoSelected){

  }else if(this.vnpaySelected){

  }
}


choosePayosSelected(){
  this.payosSelected = !this.payosSelected
  console.log("Status payos", this.payosSelected)
}


rediectPayment(){
  window.location.href = this.pagePayment
}

}

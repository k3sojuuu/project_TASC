import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OrderServiceService {

  constructor(private http:HttpClient) {}

  private createOrder = "http://localhost:8088/api/order/createOrder"

  createOrders(orderDetail: any){
     return this.http.post<any>(`${this.createOrder}`,orderDetail)
  }
}

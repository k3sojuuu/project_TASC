import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PaymentPayOs } from '../model/createPaymentPayOs.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentServiceService {

  constructor(private http: HttpClient) { }

  private UrlPaymentPayOs = "http://localhost:8002/api/payment/create"

  createPaymentPayOs(paymentData: any): Observable<any>{
    return this.http.post<any>(`${this.UrlPaymentPayOs}`, paymentData);
  }
}

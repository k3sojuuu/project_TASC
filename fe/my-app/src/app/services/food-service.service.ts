import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse  } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FoodServiceService {

  constructor(private http: HttpClient) { }

  private getFoodByGroup = "http://localhost:8085/food/getGroup"

  getFood(FoodType: string): Observable<any>{
    return this.http.get<any>(`${this.getFoodByGroup}?foodType=${FoodType}`)
  }
}
